#!/bin/bash

git fetch --tags
VERSION=$(git tag | sort -V | tail -n 1)
echo "My Latest version: $VERSION"

VERSION=1.0.0
 echo "Initial VERSION: $VERSION"
    VERSION_PARTS=(${VERSION//./ })
    echo "VERSION_PARTS: ${VERSION_PARTS[@]}"
    MAJOR=${VERSION_PARTS[0]}
    MINOR=${VERSION_PARTS[1]}
    PATCH=${VERSION_PARTS[2]}
    echo "MAJOR: $MAJOR, MINOR: $MINOR, PATCH: $PATCH"
