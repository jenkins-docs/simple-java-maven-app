from dataclasses import dataclass, field
from datetime import datetime
from typing import List, Optional, Type, cast

import marshmallow_dataclass
from typing_extensions import Literal

from pygitguardian.models import Base, BaseSchema, FromDictMixin


@dataclass
class SCAIgnoredVulnerability(Base, FromDictMixin):
    """
    A model of an ignored vulnerability for SCA. This allows to ignore all occurrences
    of a given vulnerability in a given dependency file.
    - identifier: identifier (currently: GHSA id) of the vulnerability to ignore
    - path: the path to the file in which ignore the vulnerability
    """

    identifier: str
    path: str


SCAIgnoredVulnerabilitySchema = cast(
    Type[BaseSchema],
    marshmallow_dataclass.class_schema(SCAIgnoredVulnerability, base_schema=BaseSchema),
)
SCAIgnoredVulnerability.SCHEMA = SCAIgnoredVulnerabilitySchema()


@dataclass
class SCAScanParameters(Base, FromDictMixin):
    minimum_severity: Optional[str] = None
    ignored_vulnerabilities: List[SCAIgnoredVulnerability] = field(default_factory=list)
    ignore_not_fixable: bool = False
    ignore_fixable: bool = False


SCAScanParametersSchema = cast(
    Type[BaseSchema],
    marshmallow_dataclass.class_schema(SCAScanParameters, base_schema=BaseSchema),
)
SCAScanParameters.SCHEMA = SCAScanParametersSchema()


@dataclass
class ComputeSCAFilesResult(Base, FromDictMixin):
    sca_files: List[str]


ComputeSCAFilesResultSchema = cast(
    Type[BaseSchema],
    marshmallow_dataclass.class_schema(ComputeSCAFilesResult, base_schema=BaseSchema),
)
ComputeSCAFilesResult.SCHEMA = ComputeSCAFilesResultSchema()


@dataclass
class SCAVulnerability(Base, FromDictMixin):
    severity: str
    summary: str
    identifier: str
    cve_ids: List[str] = field(default_factory=list)
    created_at: Optional[datetime] = None
    fixed_version: Optional[str] = None
    url: Optional[str] = None
    status: Optional[str] = None
    ignored_until: Optional[datetime] = None
    ignore_reason: Optional[str] = None
    ignore_comment: Optional[str] = None


SCAVulnerabilitySchema = cast(
    Type[BaseSchema],
    marshmallow_dataclass.class_schema(SCAVulnerability, base_schema=BaseSchema),
)
SCAVulnerability.SCHEMA = SCAVulnerabilitySchema()

SCADependencyType = Literal["direct", "transitive"]


@dataclass
class SCAVulnerablePackageVersion(Base, FromDictMixin):
    package_full_name: str
    version: str
    ecosystem: str
    dependency_type: Optional[SCADependencyType] = None
    vulns: List[SCAVulnerability] = field(default_factory=list)


SCAVulnerablePackageVersionSchema = cast(
    Type[BaseSchema],
    marshmallow_dataclass.class_schema(
        SCAVulnerablePackageVersion, base_schema=BaseSchema
    ),
)
SCAVulnerablePackageVersion.SCHEMA = SCAVulnerablePackageVersionSchema()


@dataclass
class SCALocationVulnerability(Base, FromDictMixin):
    location: str
    package_vulns: List[SCAVulnerablePackageVersion] = field(default_factory=list)


SCALocationVulnerabilitySchema = cast(
    Type[BaseSchema],
    marshmallow_dataclass.class_schema(
        SCALocationVulnerability, base_schema=BaseSchema
    ),
)
SCALocationVulnerability.SCHEMA = SCALocationVulnerabilitySchema()


@dataclass
class SCAScanAllOutput(Base, FromDictMixin):
    scanned_files: List[str] = field(default_factory=list)
    source_found: bool = False
    found_package_vulns: List[SCALocationVulnerability] = field(default_factory=list)


SCAScanAllOutputSchema = cast(
    Type[BaseSchema],
    marshmallow_dataclass.class_schema(SCAScanAllOutput, base_schema=BaseSchema),
)
SCAScanAllOutput.SCHEMA = SCAScanAllOutputSchema()


@dataclass
class SCAScanDiffOutput(Base, FromDictMixin):
    scanned_files: List[str] = field(default_factory=list)
    source_found: bool = False
    added_vulns: List[SCALocationVulnerability] = field(default_factory=list)
    removed_vulns: List[SCALocationVulnerability] = field(default_factory=list)


SCAScanDiffOutputSchema = cast(
    Type[BaseSchema],
    marshmallow_dataclass.class_schema(SCAScanDiffOutput, base_schema=BaseSchema),
)
SCAScanDiffOutput.SCHEMA = SCAScanDiffOutputSchema()
