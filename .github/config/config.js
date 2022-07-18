'use strict'
const config = require('conventional-changelog-conventionalcommits');

module.exports = config({
    "types": [
        { type: 'feat', section: '🚀 Features' },
        { type: 'fix', section: '🐛 Fixes' },
        { type: 'perf', section: '🐎 Performance Improvements' },
        { type: 'revert', section: '⏪ Reverts' },
        { type: 'build', section: '🛠 Build System' },
        { type: 'deps', section: '🛠 Dependency' },
        { type: 'ci', section: '💡 Continuous Integration' },
        { type: 'refactor', section: '🔥 Refactorings' },
        { type: 'doc', section: '📚 Documentation' },
        { type: 'docs', section: '📚 Documentation' },
        { type: 'style', section: '💄 Styles' },
        { type: 'test', section: '✅ Tests' },
        { type: 'wip', hidden: true },
        { type: 'chore', hidden: true },
    ]
})

module.exports = config({
    "issuePrefixes": ["TN-"],
    "issueUrlFormat": "https://greenmarimba1.atlassian.net/browse/{{prefix}}{{id}}"
})
