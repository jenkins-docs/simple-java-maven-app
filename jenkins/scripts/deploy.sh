#!/usr/bin/env bash
set -x

#Constants
CLUSTER=tn-services-cluster 
FAMILY=tn_hello_world
TASK_NAME=tn_hello_world
SERVICE_NAME=tn_hello_world
FILEJSON=/var/tmp/taskdef.json

#Get latest revision
REVISION=`aws ecs describe-task-definition --task-definition ${TASK_NAME} --region ${AWS_DEFAULT_REGION} | jq .taskDefinition.revision`

aws ecs describe-task-definition --task-definition ${TASK_NAME}:${REVISION} > ${FILEJSON}

#Register the task definition in the repository
aws ecs register-task-definition --family ${FAMILY} --cli-input-json file://${FILEJSON} --region ${AWS_DEFAULT_REGION}

#Review services
SERVICES=`aws ecs describe-services --services ${SERVICE_NAME} --cluster ${CLUSTER} --region ${AWS_DEFAULT_REGION} | jq .failures[]`

#Create or update service
if [ "$SERVICES" == "" ]; then
  echo "entered existing service"
  DESIRED_COUNT=`aws ecs describe-services --services ${SERVICE_NAME} --cluster ${CLUSTER} --region ${AWS_DEFAULT_REGION} | jq .services[].desiredCount`
  if [ ${DESIRED_COUNT} = "0" ]; then
    DESIRED_COUNT="1"
  fi
  aws ecs update-service --cluster ${CLUSTER} --region ${AWS_DEFAULT_REGION} --service ${SERVICE_NAME} --task-definition ${FAMILY}:${REVISION} --desired-count ${DESIRED_COUNT}
else
  echo "entered new service"
  aws ecs create-service --service-name ${SERVICE_NAME} --desired-count 1 --task-definition ${FAMILY} --cluster ${CLUSTER} --region ${AWS_DEFAULT_REGION}
fi