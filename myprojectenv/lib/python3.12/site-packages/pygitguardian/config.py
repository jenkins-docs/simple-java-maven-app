DEFAULT_BASE_URI = "https://api.gitguardian.com"
DEFAULT_API_VERSION = "v1"
DEFAULT_TIMEOUT = 20.0  # 20s default timeout

MULTI_DOCUMENT_LIMIT = 20
DOCUMENT_SIZE_THRESHOLD_BYTES = 1048576  # 1MB
MAXIMUM_PAYLOAD_SIZE = 2621440  # 25MB


DEFAULT_REWRITE_GIT_HISTORY_MESSAGE = """
  To prevent having to rewrite git history in the future, setup ggshield as a pre-commit hook:
    https://docs.gitguardian.com/ggshield-docs/integrations/git-hooks/pre-commit
"""

DEFAULT_PRE_COMMIT_MESSAGE = """> How to remediate

  Since the secret was detected before the commit was made:
  1. replace the secret with its reference (e.g. environment variable).
  2. commit again.

> [To apply with caution] If you want to bypass ggshield (false positive or other reason), run:
  - if you use the pre-commit framework:

    SKIP=ggshield git commit -m "<your message>"""

DEFAULT_PRE_PUSH_MESSAGE = (
    """> How to remediate

  Since the secret was detected before the push BUT after the commit, you need to:
  1. rewrite the git history making sure to replace the secret with its reference (e.g. environment variable).
  2. push again.
"""
    + DEFAULT_REWRITE_GIT_HISTORY_MESSAGE
    + """
> [To apply with caution] If you want to bypass ggshield (false positive or other reason), run:
  - if you use the pre-commit framework:

    SKIP=ggshield-push git push"""
)

DEFAULT_PRE_RECEIVE_MESSAGE = (
    """> How to remediate

  A pre-receive hook set server side prevented you from pushing secrets.

  Since the secret was detected during the push BUT after the commit, you need to:
  1. rewrite the git history making sure to replace the secret with its reference (e.g. environment variable).
  2. push again.
"""
    + DEFAULT_REWRITE_GIT_HISTORY_MESSAGE
    + """
> [To apply with caution] If you want to bypass ggshield (false positive or other reason), run:

    git push -o breakglass"""
)
