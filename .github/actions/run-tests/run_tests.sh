#!/usr/bin/env bash
set -euo pipefail

SHOW_LOGS="$1"
REMOVE_COMPOSE_DATA="$2"
COMPOSE_FILE_PATH="$3"
PREFIX="$4"
TEST_CONTAINER_TITLE="$5"

echo "### Create test_files_volume"
docker volume create test_files_volume

echo "### Run tests"
docker compose -f $COMPOSE_FILE_PATH up -d
docker ps -a
docker wait $TEST_CONTAINER_TITLE

if [ "$SHOW_LOGS" = "true" ]; then
  echo "### Test logs ###"
  docker logs $TEST_CONTAINER_TITLE
fi

if [ "$REMOVE_COMPOSE_DATA" = "true" ]; then

  echo "### Close and remove compose containers ###"
  docker compose rm -sf

  echo "### Remove test container image $PREFIX/$TEST_CONTAINER_TITLE:latest"
  docker image rm $PREFIX/$TEST_CONTAINER_TITLE:latest

  echo "### Remove test_files_volume"
  docker volume rm test_files_volume

fi