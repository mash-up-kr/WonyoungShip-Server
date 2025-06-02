#!/bin/bash
source .env

#REGISTRY_URL=${REGISTRY_URL}
IMAGE_NAME="wonyoungship/doong2"
TAG="latest"
CONTAINER_NAME="doong2"
HEALTH_CHECK_URI="/actuator/health"

docker login -u ${DOCKER_HUB_USERNAME} -p ${DOCKER_HUB_PASSWORD} # ${REGISTRY_URL}

docker pull ${IMAGE_NAME}:${TAG}

if [ "$(docker ps -a -q -f name=${CONTAINER_NAME})" ]; then
  docker stop ${CONTAINER_NAME}
  docker rm ${CONTAINER_NAME}
fi

# 서버 실행
echo "> Run Docker"
docker run -d -p 8080:8080 --memory=1g --name ${CONTAINER_NAME} \
  -e DOCKER_HUB_USERNAME=${DOCKER_HUB_USERNAME} \
  -e DOCKER_HUB_PASSWORD=${DOCKER_HUB_PASSWORD} \
  ${IMAGE_NAME}:${TAG}

echo "----------------------------------------------------------------------"

# 서버 실행 확인
sleep 15

for retry_count in {1..15}
do
    echo "> Health check"

    response=$(curl -s http://localhost:8080${HEALTH_CHECK_URI})
    # shellcheck disable=SC2126
    up_count=$(echo "${response}" | grep 'UP' | wc -l)

    if [ "${up_count}" -ge 1 ]
    then
        echo "> SUCCESS"
        break
    else
        echo "> Not run yet"
        echo "> Response: ${response}"
    fi

    if [ "${retry_count}" -eq 15 ]
		then
        echo "> Failed to running server"
        docker rm -f ${CONTAINER_NAME}
        exit 1
    fi

    sleep 2
done

echo "----------------------------------------------------------------------"
