#!/bin/bash

settings=${MLS_M2_SETTINGS:-~/.m2/settings.xml}
module=auth-service
app=auth-service

echo "Running Docker build for ${app} with secrets from '${settings}'..."

DOCKER_BUILDKIT=1 docker build \
    --secret id=m2-settings,src="${settings}" \
    --build-arg MODULE=$module --build-arg APP=$app \
    .
