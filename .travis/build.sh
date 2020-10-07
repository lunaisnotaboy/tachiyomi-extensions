#!/bin/bash

set -e

./gradlew --no-daemon clean assembleRelease

TOOLS="$(ls -d ${ANDROID_HOME}/build-tools/* | tail -1)"

shopt -s globstar nullglob extglob
APKS=( **/*"-unsigned.apk"* )

DEST=$PWD/apk

rm -rf $DEST && mkdir -p $DEST

for APK in ${APKS[@]}; do
    BASENAME=$(basename $APK)
    APKNAME="${BASENAME%%+(-release*)}.apk"
    APKDEST="$DEST/$APKNAME"

    ${TOOLS}/zipalign -v -p 4 $APK $APKDEST
done
