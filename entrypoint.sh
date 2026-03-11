#!/bin/bash

echo "Running gradlew with TAG_EXPR: \"$TAG_EXPR\" and TEST_ARGS: \"$TEST_ARGS\""

exec xvfb-run --server-args="-screen ${DISPLAY#:} ${XVFB_WHD}" -a ./gradlew test \
-Djunit.jupiter.extensions.autodetection.enabled=true \
$TEST_ARGS