// See https://github.com/TriPSs/conventional-changelog-action#pre-changelog-generation-hook
// RJM 7/3/22
// for reference only - not used
// Basic implementation to change version strings in a file location, where
// where string  'VERSION = '0.6.1'.freeze - is replaced
const path = require('path');
const fs = require('fs');
const semver = require('semver');
const core = require('@actions/core');
const childProcess = require("child_process");

exports.preVersionGeneration = (version) => {
  const { GITHUB_WORKSPACE } = process.env;
  core.info(`Computed version bump: ${version}`);

  const version_info_file = path.join(GITHUB_WORKSPACE, 'version.txt');
  const version_info = `${fs.readFileSync(version_info_file)}`;
  core.info(`Current version info: ${version_info}`);

  currentVersion = version_info.match(/VERSION\s*=\s'(.*)'/)[1];
  core.info(`Current version: ${currentVersion}`);

  if (semver.lt(version, currentVersion)) { version = currentVersion; }
  core.info(`Final version: ${version}`);

  const new_version_info = version_info.replace(/VERSION\s*=\s*.*/g, `VERSION = '${version}'`);
  core.info(`Updated gem info: ${new_version_info}`);
  fs.writeFileSync(version_info_file, new_version_info);

    //   const launchOption = { cwd: GITHUB_WORKSPACE };
    //   childProcess.execSync('bundle config unset deployment', launchOption);
    //   childProcess.execSync('bundle install', launchOption);
    //   childProcess.execSync('bundle exec rake update --trace', launchOption);
    //   childProcess.execSync('bundle config deployment true', launchOption);
    //   return version;
}

exports.preTagGeneration = (tag) => { }