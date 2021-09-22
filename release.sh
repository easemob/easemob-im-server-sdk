#!/usr/bin/env bash

# usage: ./release.sh 当前Release版本 下一个开发版本

RELEASE=$1
SNAPSHOT=$2

mvn versions:set -DnewVersion=$RELEASE -DgenerateBackupPoms=false
git add .
git commit --message "v$RELEASE Release"

# Tag the release
git tag -s v$RELEASE -m "v$RELEASE"

# Bump up the version in pom.xml to the next snapshot
mvn versions:set -DnewVersion="$SNAPSHOT-SNAPSHOT" -DgenerateBackupPoms=false
git add .
git commit --message "v$SNAPSHOT Development"

git push && git push --tags

mvn deploy
