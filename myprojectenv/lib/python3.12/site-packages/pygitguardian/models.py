# pyright: reportIncompatibleVariableOverride=false
# Disable this check because of multiple non-dangerous violations (SCHEMA variables,
# BaseSchema.Meta class)
from dataclasses import dataclass, field
from datetime import date, datetime
from enum import Enum
from typing import Any, ClassVar, Dict, List, Literal, Optional, Type, cast
from uuid import UUID

import marshmallow_dataclass
from marshmallow import (
    EXCLUDE,
    Schema,
    ValidationError,
    fields,
    post_load,
    pre_load,
    validate,
)
from typing_extensions import Self

from .config import (
    DEFAULT_PRE_COMMIT_MESSAGE,
    DEFAULT_PRE_PUSH_MESSAGE,
    DEFAULT_PRE_RECEIVE_MESSAGE,
    DOCUMENT_SIZE_THRESHOLD_BYTES,
    MULTI_DOCUMENT_LIMIT,
)


class ToDictMixin:
    """
    Provides a type-safe `to_dict()` method for classes using Marshmallow
    """

    SCHEMA: ClassVar[Schema]

    def to_dict(self) -> Dict[str, Any]:
        return cast(Dict[str, Any], self.SCHEMA.dump(self))


class FromDictMixin:
    """This class must be used as an additional base class for all classes whose schema
    implements a `post_load` function turning the received dict into a class instance.

    It makes it possible to deserialize an object using `MyClass.from_dict(dct)` instead
    of `MyClass.SCHEMA.load(dct)`. The `from_dict()` method is shorter, but more
    importantly, type-safe: its return type is an instance of `MyClass`, not
    `list[Any] | Any`.

    Reference: https://marshmallow.readthedocs.io/en/stable/quickstart.html#deserializing-to-objects  E501
    """

    SCHEMA: ClassVar[Schema]

    @classmethod
    def from_dict(cls, dct: Dict[str, Any]) -> Self:
        return cast(Self, cls.SCHEMA.load(dct))


class BaseSchema(Schema):
    class Meta:
        ordered = True
        unknown = EXCLUDE


class Base(ToDictMixin):
    def __init__(self, status_code: Optional[int] = None) -> None:
        self.status_code = status_code

    def to_json(self) -> str:
        """
        to_json converts model to JSON string.
        """
        return cast(str, self.SCHEMA.dumps(self))

    @property
    def success(self) -> bool:
        return self.__bool__()

    def __bool__(self) -> bool:
        return self.status_code == 200


class DocumentSchema(BaseSchema):
    filename = fields.String(validate=validate.Length(max=256), allow_none=True)
    document = fields.String(required=True)

    @staticmethod
    def validate_size(document: Dict[str, Any], maximum_size: int) -> None:
        """Raises a ValidationError if the content of the document is longer than
        `maximum_size`.

        This is not implemented as a Marshmallow validator because the maximum size can
        vary.
        """
        encoded = document["document"].encode("utf-8", errors="replace")
        if len(encoded) > maximum_size:
            raise ValidationError(
                f"file exceeds the maximum allowed size of {maximum_size}B"
            )

    @post_load
    def replace_0_bytes(self, in_data: Dict[str, Any], **kwargs: Any) -> Dict[str, Any]:
        doc = in_data["document"]
        # Our API does not accept 0 bytes in documents so replace them with
        # the ASCII substitute character.
        # We no longer uses the Unicode replacement character (U+FFFD) because
        # it makes the encoded string one byte longer, making it possible to
        # hit the maximum size limit.
        in_data["document"] = doc.replace("\0", "\x1a")
        return in_data

    @post_load
    def force_utf_8_encoding(
        self, in_data: Dict[str, Any], **kwargs: Any
    ) -> Dict[str, Any]:
        doc = in_data["document"]
        # Force UTF-8 and substitute ? for encoding errors
        in_data["document"] = doc.encode("utf-8", errors="replace").decode("utf-8")
        return in_data


class Document(Base):
    """
    Document is a request object for communicating documents
    to the API

    Attributes:
        filename (optional,str): filename for filename evaluation
        document (str): text content
    """

    SCHEMA = DocumentSchema()

    def __init__(self, document: str, filename: Optional[str] = None, **kwargs: Any):
        super().__init__()
        self.document = document
        if filename:
            self.filename = filename

    def __repr__(self) -> str:
        return f"filename:{self.filename}, document:{self.document}"


class DetailSchema(BaseSchema):
    detail = fields.String(required=True)

    @pre_load
    def rename_errors(
        self, data: Dict[str, Any], many: bool, **kwargs: Any
    ) -> Dict[str, Any]:
        error = data.pop("error", None)
        if error is not None:
            data["detail"] = str(error)

        return data

    @post_load
    def make_detail_response(self, data: Dict[str, Any], **kwargs: Any) -> "Detail":
        return Detail(**data)


class Detail(Base, FromDictMixin):
    """Detail is a response object mostly returned on error or when the
    api output is a simple string.

    Attributes:
        detail (str): response string
    """

    SCHEMA = DetailSchema()

    def __init__(
        self, detail: str, status_code: Optional[int] = None, **kwargs: Any
    ) -> None:
        super().__init__(status_code=status_code)
        self.detail = detail

    def __repr__(self) -> str:
        return f"{self.status_code}:{self.detail}"


class MatchSchema(BaseSchema):
    match = fields.String(required=True)
    match_type = fields.String(data_key="type", required=True)
    line_start = fields.Int(allow_none=True)
    line_end = fields.Int(allow_none=True)
    index_start = fields.Int(allow_none=True)
    index_end = fields.Int(allow_none=True)

    @post_load
    def make_match(self, data: Dict[str, Any], **kwargs: Any) -> "Match":
        return Match(**data)


class Match(Base, FromDictMixin):
    """
    Match describes an issue found by GitGuardian.

    Fields:

    - match: the matched string

    - match_type: the "label" of the matched string ("username", "password"...)

    - index_start: 0-based index of the first character of the match inside the
      document.

    - index_end: 0-based index of the last character of the match inside the
      document (not the index of the character after the last character!)

    - line_start: 1-based index of the line where the first character of the
      match is.

    - line_end: 1-based index of the line where the last character of the
      match is.
    """

    SCHEMA = MatchSchema()

    def __init__(
        self,
        match: str,
        match_type: str,
        line_start: Optional[int] = None,
        line_end: Optional[int] = None,
        index_start: Optional[int] = None,
        index_end: Optional[int] = None,
        **kwargs: Any,
    ) -> None:
        super().__init__()
        self.match = match
        self.match_type = match_type
        self.line_start = line_start
        self.line_end = line_end
        self.index_start = index_start
        self.index_end = index_end

    def __repr__(self) -> str:
        return (
            "match:{}, "
            "match_type:{}, "
            "line_start:{}, "
            "line_end:{}, "
            "index_start:{}, "
            "index_end:{}".format(
                self.match,
                self.match_type,
                repr(self.line_start),
                repr(self.line_end),
                repr(self.index_start),
                repr(self.index_end),
            )
        )


class PolicyBreakSchema(BaseSchema):
    break_type = fields.String(data_key="type", required=True)
    policy = fields.String(required=True)
    validity = fields.String(required=False, load_default=None, dump_default=None)
    known_secret = fields.Boolean(required=False, load_default=False, dump_default=None)
    incident_url = fields.String(required=False, load_default=False, dump_default=None)
    matches = fields.List(fields.Nested(MatchSchema), required=True)

    @post_load
    def make_policy_break(self, data: Dict[str, Any], **kwargs: Any) -> "PolicyBreak":
        return PolicyBreak(**data)


class PolicyBreak(Base, FromDictMixin):
    """
    PolicyBreak describes a GitGuardian policy break found
    in a scan.
    A PolicyBreak can contain multiple matches, for example,
    on secrets that have a client id and client secret.
    """

    SCHEMA = PolicyBreakSchema()

    def __init__(
        self,
        break_type: str,
        policy: str,
        validity: str,
        matches: List[Match],
        known_secret: bool = False,
        incident_url: Optional[str] = None,
        **kwargs: Any,
    ) -> None:
        super().__init__()
        self.break_type = break_type
        self.policy = policy
        self.validity = validity
        self.known_secret = known_secret
        self.incident_url = incident_url
        self.matches = matches

    @property
    def is_secret(self) -> bool:
        return self.policy == "Secrets detection"

    def __repr__(self) -> str:
        return (
            "break_type:{}, "
            "policy:{}, "
            "matches: {}".format(self.break_type, self.policy, repr(self.matches))
        )


class ScanResultSchema(BaseSchema):
    policy_break_count = fields.Integer(required=True)
    policies = fields.List(fields.String(), required=True)
    policy_breaks = fields.List(fields.Nested(PolicyBreakSchema), required=True)

    @post_load
    def make_scan_result(self, data: Dict[str, Any], **kwargs: Any) -> "ScanResult":
        return ScanResult(**data)


class ScanResult(Base, FromDictMixin):
    """ScanResult is a response object returned on a Content Scan

    Attributes:
        status_code (int): response status code
        policy_break_count (int): number of policy breaks
        policy_breaks (List): policy break list
        policies (List[str]): string list of policies evaluated
    """

    SCHEMA = ScanResultSchema()

    def __init__(
        self,
        policy_break_count: int,
        policy_breaks: List[PolicyBreak],
        policies: List[str],
        **kwargs: Any,
    ) -> None:
        """
        :param policy_break_count: number of policy breaks
        :type policy_break_count: int
        :param policy_breaks: policy break list
        :type policy_breaks: List
        :param policies: string list of policies evaluated
        :type policies: List[str]
        """
        super().__init__()
        self.policy_break_count = policy_break_count
        self.policies = policies
        self.policy_breaks = policy_breaks

    @property
    def has_policy_breaks(self) -> bool:
        """has_secrets is an easy way to check if your provided document has policy breaks

        >>> obj = ScanResult(2, [], [])
        >>> obj.has_policy_breaks
        True

        :return: true if there were policy breaks (including secrets) in the document
        :rtype: bool
        """

        return self.policy_break_count > 0

    @property
    def has_secrets(self) -> bool:
        """has_secrets is an easy way to check if your provided document has secrets

        :return: true if there were secrets in the document
        :rtype: bool
        """

        return any(policy_break.is_secret for policy_break in self.policy_breaks)

    def __repr__(self) -> str:
        return (
            "policy_break_count:{}, "
            "policies:{}, "
            "policy_breaks: {}".format(
                self.policy_break_count, self.policies, self.policy_breaks
            )
        )

    def __str__(self) -> str:
        return "{} policy breaks from the evaluated policies: {}".format(
            self.policy_break_count,
            ", ".join(policy_break.policy for policy_break in self.policy_breaks),
        )


class MultiScanResultSchema(BaseSchema):
    scan_results = fields.List(
        fields.Nested(ScanResultSchema),
        required=True,
        validate=validate.Length(min=1),
    )

    @post_load
    def make_scan_result(
        self, data: Dict[str, Any], **kwargs: Any
    ) -> "MultiScanResult":
        return MultiScanResult(**data)


class MultiScanResult(Base, FromDictMixin):
    """ScanResult is a response object returned on a Content Scan

    Attributes:
        status_code (int): response status code
        policy_break_count (int): number of policy breaks
        policy_breaks (List): policy break list
        policies (List[str]): string list of policies evaluated
    """

    SCHEMA = MultiScanResultSchema()

    def __init__(self, scan_results: List[ScanResult], **kwargs: Any) -> None:
        """
        :param scan_results: List of scan_results
        """
        super().__init__()
        self.scan_results = scan_results

    @property
    def has_policy_breaks(self) -> bool:
        """has_policy_breaks is an easy way to check if your provided document has policy breaks

        >>> obj = ScanResult(2, [], [])
        >>> obj.has_policy_breaks
        True

        :return: true if there were policy breaks (including secrets) in the documents
        :rtype: bool
        """

        return any(scan_result.has_policy_breaks for scan_result in self.scan_results)

    @property
    def has_secrets(self) -> bool:
        """has_secrets is an easy way to check if your provided document has secrets

        :return: true if there were secrets in the documents
        :rtype: bool
        """

        return any(scan_result.has_secrets for scan_result in self.scan_results)

    def __repr__(self) -> str:
        return f"scan_results:{self.scan_results}"

    def __str__(self) -> str:
        return "{} scan results containing {} policy breaks".format(
            len(self.scan_results),
            len(
                [
                    policy_break
                    for scan_result in self.scan_results
                    for policy_break in scan_result.policy_breaks
                ]
            ),
        )


class QuotaSchema(BaseSchema):
    count = fields.Int()
    limit = fields.Int()
    remaining = fields.Int()
    since = fields.Date()

    @post_load
    def make_quota(self, data: Dict[str, Any], **kwargs: Any) -> "Quota":
        return Quota(**data)


class Quota(Base, FromDictMixin):
    """
    Quota describes a quota category in the GitGuardian API.
    Allows you to check your current available quota.
    Example:
    {"count": 2,
    "limit": 5000,
    "remaining": 4998,
    "since": "2021-04-18"}
    """

    SCHEMA = QuotaSchema()

    def __init__(
        self, count: int, limit: int, remaining: int, since: date, **kwargs: Any
    ) -> None:
        super().__init__()
        self.count = count
        self.limit = limit
        self.remaining = remaining
        self.since = since

    def __repr__(self) -> str:
        return (
            "count:{}, "
            "limit:{}, "
            "remaining:{}, "
            "since:{}".format(
                self.count, self.limit, self.remaining, self.since.isoformat()
            )
        )


class QuotaResponseSchema(BaseSchema):
    content = fields.Nested(QuotaSchema)

    @post_load
    def make_quota_response(
        self, data: Dict[str, Any], **kwargs: Any
    ) -> "QuotaResponse":
        return QuotaResponse(**data)


class QuotaResponse(Base, FromDictMixin):
    """
    Quota describes a quota category in the GitGuardian API.
    Allows you to check your current available quota.
    Example:
    {"content": {
        "count": 2,
        "limit": 5000,
        "remaining": 4998,
        "since": "2021-04-18"}}
    """

    SCHEMA = QuotaResponseSchema()

    def __init__(self, content: Quota, **kwargs: Any) -> None:
        super().__init__()
        self.content = content

    def __repr__(self) -> str:
        return f"content:{repr(self.content)}"


class HoneytokenResponseSchema(BaseSchema):
    id = fields.UUID()
    name = fields.String()
    description = fields.String(allow_none=True)
    created_at = fields.AwareDateTime()
    gitguardian_url = fields.URL()
    status = fields.String()
    triggered_at = fields.AwareDateTime(allow_none=True)
    revoked_at = fields.AwareDateTime(allow_none=True)
    open_events_count = fields.Int(allow_none=True)
    type_ = fields.String(data_key="type")
    creator_id = fields.Int(allow_none=True)
    revoker_id = fields.Int(allow_none=True)
    creator_api_token_id = fields.String(allow_none=True)
    revoker_api_token_id = fields.String(allow_none=True)
    token = fields.Mapping(fields.String(), fields.String())
    tags = fields.List(fields.String())

    @post_load
    def make_honeytoken_response(
        self, data: Dict[str, Any], **kwargs: Any
    ) -> "HoneytokenResponse":
        return HoneytokenResponse(**data)


class HoneytokenResponse(Base, FromDictMixin):
    """
    honeytoken creation in the GitGuardian API.
    Allows users to create and get a honeytoken.
    Example:
        {
            "id": "d45a123f-b15d-4fea-abf6-ff2a8479de5b",
            "name": "honeytoken A",
            "description": "honeytoken used in the repository AA",
            "created_at": "2019-08-22T14:15:22Z",
            "gitguardian_url":
                "https://dashboard.gitguardian.com/workspace/1/honeytokens/d45a123f-b15d-4fea-abf6-ff2a8479de5b",
            "status": "active",
            "triggered_at": "2019-08-22T14:15:22Z",
            "revoked_at": "2019-08-22T14:15:22Z",
            "open_events_count": 122,
            "type": "AWS",
            "creator_id": 122,
            "revoker_id": 122,
            "creator_api_token_id": null,
            "revoker_api_token_id": null,
            "token": {
                "access_token_id": "AAAA",
                "secret_key": "BBB"
            },
        "tags": ["publicly_exposed"]
        }
    """

    SCHEMA = HoneytokenResponseSchema()

    def __init__(
        self,
        id: UUID,
        name: str,
        description: Optional[str],
        created_at: datetime,
        gitguardian_url: str,
        status: str,
        triggered_at: Optional[datetime],
        revoked_at: Optional[datetime],
        open_events_count: Optional[int],
        type_: str,
        creator_id: Optional[int],
        revoker_id: Optional[int],
        creator_api_token_id: Optional[str],
        revoker_api_token_id: Optional[str],
        token: Dict[str, str],
        tags: List[str],
        **kwargs: Any,
    ) -> None:
        super().__init__()
        self.id = id
        self.name = name
        self.description = description
        self.created_at = created_at
        self.gitguardian_url = gitguardian_url
        self.status = status
        self.triggered_at = triggered_at
        self.revoked_at = revoked_at
        self.open_events_count = open_events_count
        self.type_ = type_
        self.creator_id = creator_id
        self.revoker_id = revoker_id
        self.creator_api_token_id = creator_api_token_id
        self.revoker_api_token_id = revoker_api_token_id
        self.token = token
        self.tags = tags

    def __repr__(self) -> str:
        return f"honeytoken:{self.id} {self.name}"


class HoneytokenWithContextResponseSchema(BaseSchema):
    content = fields.String()
    filename = fields.String()
    language = fields.String()
    suggested_commit_message = fields.String()
    honeytoken_id = fields.UUID()
    gitguardian_url = fields.URL()

    @post_load
    def make_honeytoken_with_context_response(
        self, data: Dict[str, Any], **kwargs: Any
    ) -> "HoneytokenWithContextResponse":
        return HoneytokenWithContextResponse(**data)


class HoneytokenWithContextResponse(Base, FromDictMixin):
    """
    honeytoken creation with context in the GitGuardian API.
    Allows users to get a file where a new honeytoken is.
    Example:
        {
            "content": "def return_aws_credentials():\n \
                            aws_access_key_id = XXXXXXXX\n \
                            aws_secret_access_key = XXXXXXXX\n \
                            aws_region = us-west-2",\n \
                            return (aws_access_key_id, aws_secret_access_key, aws_region)\n",
            "filename": "aws.py",
            "language": "python",
            "suggested_commit_message": "Add AWS credentials",
            "honeytoken_id": "d45a123f-b15d-4fea-abf6-ff2a8479de5b",
            "gitguardian_url":
                "https://dashboard.gitguardian.com/workspace/1/honeytokens/d45a123f-b15d-4fea-abf6-ff2a8479de5b",
        }
    """

    SCHEMA = HoneytokenWithContextResponseSchema()

    def __init__(
        self,
        content: str,
        filename: str,
        language: str,
        suggested_commit_message: str,
        honeytoken_id: UUID,
        gitguardian_url: str,
        **kwargs: Any,
    ) -> None:
        super().__init__()
        self.content = content
        self.filename = filename
        self.language = language
        self.suggested_commit_message = suggested_commit_message
        self.honeytoken_id = honeytoken_id
        self.gitguardian_url = gitguardian_url

    def __repr__(self) -> str:
        return f"honeytoken_context:{self.filename}"


class HealthCheckResponseSchema(BaseSchema):
    detail = fields.String(allow_none=False)
    status_code = fields.Int(allow_none=False)
    app_version = fields.String(allow_none=True)
    secrets_engine_version = fields.String(allow_none=True)


class HealthCheckResponse(Base):
    SCHEMA = HealthCheckResponseSchema()

    def __init__(
        self,
        detail: str,
        status_code: int,
        app_version: Optional[str] = None,
        secrets_engine_version: Optional[str] = None,
        **kwargs: Any,
    ):
        super().__init__()
        self.detail = detail
        self.status_code = status_code
        self.app_version = app_version
        self.secrets_engine_version = secrets_engine_version

    def __repr__(self) -> str:
        return (
            "detail:{}, "
            "status_code:{}, "
            "app version:{}, "
            "secrets engine version:{}".format(
                self.detail,
                self.status_code,
                self.app_version or "",
                self.secrets_engine_version or "",
            )
        )


@dataclass
class SecretScanPreferences:
    maximum_document_size: int = DOCUMENT_SIZE_THRESHOLD_BYTES
    maximum_documents_per_scan: int = MULTI_DOCUMENT_LIMIT


@dataclass
class RemediationMessages:
    pre_commit: str = DEFAULT_PRE_COMMIT_MESSAGE
    pre_push: str = DEFAULT_PRE_PUSH_MESSAGE
    pre_receive: str = DEFAULT_PRE_RECEIVE_MESSAGE


@dataclass
class ServerMetadata(Base, FromDictMixin):
    version: str
    preferences: Dict[str, Any]
    secret_scan_preferences: SecretScanPreferences = field(
        default_factory=SecretScanPreferences
    )
    remediation_messages: RemediationMessages = field(
        default_factory=RemediationMessages
    )


ServerMetadata.SCHEMA = cast(
    BaseSchema,
    marshmallow_dataclass.class_schema(ServerMetadata, base_schema=BaseSchema)(),
)


class JWTResponseSchema(BaseSchema):
    token = fields.String(required=True)

    @post_load
    def make_response(self, data: Dict[str, str], **kwargs: Any) -> "JWTResponse":
        return JWTResponse(**data)


class JWTResponse(Base, FromDictMixin):
    """Token to use the HasMySecretLeaked service.

    Attributes:
        token (str): the JWT token
    """

    SCHEMA = JWTResponseSchema()

    def __init__(self, token: str, **kwargs: Any) -> None:
        super().__init__()
        self.token = token

    def __repr__(self) -> str:
        return self.token


class JWTService(Enum):
    """Enum for the different services GIM can generate a JWT for."""

    HMSL = "hmsl"


@dataclass
class Detector(Base, FromDictMixin):
    name: str
    display_name: str
    nature: str
    family: str
    detector_group_name: str
    detector_group_display_name: str


Severity = Literal["low", "medium", "high", "critical", "unknown"]
ValidityStatus = Literal["valid", "invalid", "failed_to_check", "no_checker", "unknown"]
IncidentStatus = Literal["IGNORED", "TRIGGERED", "RESOLVED", "ASSIGNED"]
Tag = Literal[
    "DEFAULT_BRANCH",
    "FROM_HISTORICAL_SCAN",
    "CHECK_RUN_SKIP_FALSE_POSITIVE",
    "CHECK_RUN_SKIP_LOW_RISK",
    "CHECK_RUN_SKIP_TEST_CRED",
    "IGNORED_IN_CHECK_RUN",
    "FALSE_POSITIVE",
    "PUBLICLY_EXPOSED",
    "PUBLICLY_LEAKED",
    "REGRESSION",
    "SENSITIVE_FILE",
    "TEST_FILE",
]
IgnoreReason = Literal["test_credential", "false_positive", "low_risk"]
OccurrenceKind = Literal["realtime", "historical"]
OccurrencePresence = Literal["present", "removed"]
Visibility = Literal["private", "internal", "public"]


@dataclass
class SecretPresence(Base, FromDictMixin):
    files_requiring_code_fix: int
    files_pending_merge: int
    files_fixed: int
    outside_vcs: int
    removed_outside_vcs: int
    in_vcs: int
    removed_in_vcs: int


@dataclass
class Answer(Base, FromDictMixin):
    type: str
    field_ref: str
    field_label: str
    boolean: Optional[bool] = None
    text: Optional[str] = None


@dataclass
class Feedback(Base, FromDictMixin):
    created_at: datetime
    updated_at: datetime
    member_id: int
    email: str
    answers: List[Answer]


@dataclass
class Source(Base, FromDictMixin):
    id: int
    url: str
    type: str
    full_name: str
    health: Literal["safe", "unknown", "at_risk"]
    default_branch: Optional[str]
    default_branch_head: Optional[str]
    open_incidents_count: int
    closed_incidents_count: int
    secret_incidents_breakdown: Dict[str, Any]  # TODO: add SecretIncidentsBreakdown
    visibility: Visibility
    external_id: str
    source_criticality: str
    last_scan: Optional[Dict[str, Any]]  # TODO: add LastScan
    monitored: bool


@dataclass
class OccurrenceMatch(Base, FromDictMixin):
    """
    Describes the match of an occurrence, different from the Match return as part of a PolicyBreak.

    name: type of the match such as "api_key", "password", "client_id", "client_secret"...
    indice_start: start index of the match in the document (0-based)
    indice_end: end index of the match in the document (0-based, strictly greater than indice_start)
    pre_line_start: Optional start line number (1-based) of the match in the document (before the git patch)
    pre_line_end: Optional end line number (1-based) of the match in the document (before the git patch)
    post_line_start: Optional start line number (1-based) of the match in the document (after the git patch)
    post_line_end: Optional end line number (1-based) of the match in the document (after the git patch)
    """

    name: str
    indice_start: int
    indice_end: int
    pre_line_start: Optional[int]
    pre_line_end: Optional[int]
    post_line_start: Optional[int]
    post_line_end: Optional[int]


@dataclass
class SecretOccurrence(Base, FromDictMixin):
    id: int
    incident_id: int
    kind: OccurrenceKind
    source: Source
    author_name: str
    author_info: str
    date: datetime  # Publish date
    url: str
    matches: List[OccurrenceMatch]
    tags: List[str]
    sha: Optional[str]  # Commit sha
    presence: OccurrencePresence
    filepath: Optional[str]


SecretOccurrenceSchema = cast(
    Type[BaseSchema],
    marshmallow_dataclass.class_schema(SecretOccurrence, base_schema=BaseSchema),
)
SecretOccurrence.SCHEMA = SecretOccurrenceSchema()


@dataclass(repr=False)  # the default repr would be too long
class SecretIncident(Base, FromDictMixin):
    """
    Secret Incident describes a leaked secret incident.
    """

    id: int
    date: datetime
    detector: Detector
    secret_hash: str
    hmsl_hash: str
    gitguardian_url: str
    regression: bool
    status: IncidentStatus
    assignee_id: Optional[int]
    assignee_email: Optional[str]
    occurrences_count: int
    secret_presence: SecretPresence
    ignore_reason: Optional[IgnoreReason]
    triggered_at: Optional[datetime]
    ignored_at: Optional[datetime]
    ignorer_id: Optional[int]
    ignorer_api_token_id: Optional[UUID]
    resolver_id: Optional[int]
    resolver_api_token_id: Optional[UUID]
    secret_revoked: bool
    severity: Severity
    validity: ValidityStatus
    resolved_at: Optional[datetime]
    share_url: Optional[str]
    tags: List[Tag]
    feedback_list: List[Feedback]
    occurrences: Optional[List[SecretOccurrence]]

    def __repr__(self) -> str:
        return (
            f"id:{self.id}, detector_name:{self.detector.name},"
            f"  url:{self.gitguardian_url}"
        )


SecretIncidentSchema = cast(
    Type[BaseSchema],
    marshmallow_dataclass.class_schema(SecretIncident, base_schema=BaseSchema),
)
SecretIncident.SCHEMA = SecretIncidentSchema()
