#!/bin/bash

# This script can be used to build the Docker image locally, since `docker-compose build` does not support Buildkit secrets

settings=${MLS_M2_SETTINGS:-~/.m2/settings.xml}
module=auth-service
app=auth-service
tag=wrkfmdit/mls-auth:${MLS_TAG:-latest}

echo "Running Docker build for ${app} with secrets from '${settings}'..."

DOCKER_BUILDKIT=1 docker build \
    --secret id=m2-settings,src="${settings}" \
    --build-arg MODULE=$module --build-arg APP=$app \
    -t "$tag" .
