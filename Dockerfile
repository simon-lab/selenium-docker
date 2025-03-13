FROM bellsoft/liberica-openjdk-alpine:23

# Install Curl jq
RUN apk add curl jq

# workspace
WORKDIR /home/selenium-docker

#add the required files

ADD target/docker-resources     /home/selenium-docker/
ADD runner.bat                   runner.bat

#Environment Variables
#BROWSER
#HUB_HOST
#TEST_SUITE
#THREAD_COUNT

# Start the runner.sh
ENTRYPOINT bat runner.bat