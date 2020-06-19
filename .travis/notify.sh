#!/usr/bin/env bash

message="<${TRAVIS_BUILD_WEB_URL}|Build #${TRAVIS_BUILD_NUMBER}> succeeded, library deployed. <https://github.com/${TRAVIS_REPO_SLUG}/tree/${TRAVIS_BRANCH}|Branch ${TRAVIS_BRANCH} on ${TRAVIS_REPO_SLUG}>, commit ${TRAVIS_COMMIT}: ${TRAVIS_COMMIT_MESSAGE}"
curl -f -q -X POST --data-urlencode "payload={\"channel\": \"#dw-builds\", \"username\": \"transmart-lib\", \"text\": \"${message}\"}" "${SLACK_WEBHOOK}"
