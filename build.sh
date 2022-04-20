#!/usr/bin/env bash
# exit whenever encounter errors
set -e

./build-script/build-before.sh

#gradle clean build dockerImageBuild -x test -Pprofile=dev-k8s-share

./gradlew clean build  -x test -Pprofile=dev-k8s-share

./build-script/build-after.sh
