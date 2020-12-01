#!/bin/bash

set -f

server=$DEPLOY_SERVER
user=$DEPLOY_USER
branch=$DEPLOY_BRANCH
gittoken=$DEPLOY_GITLAB_TOKEN
gituser=$DEPLOY_GITLAB_USER

echo "Deploying project on server ${server} as ${user} from branch ${branch}"

apt-get update && apt-get install -y openssh-client

# apt-get install -y gnupg2
# apt-get install -y curl
# dpkg -S add-apt-repository

# apt install apt-transport-https ca-certificates curl software-properties-common
# curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
# add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable"
# apt-cache search docker-ce

# apt update
# apt install docker-ce

# curl -L https://github.com/docker/compose/releases/download/1.21.2/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
# chmod +x /usr/local/bin/docker-compose

## Rolling Update


command="ls -ltr && \
 rm -rf /home/ubuntu/flashexplatform && \
 git clone https://${gituser}:${gittoken}@gitlab.stackroute.in/flashex/flashexplatform.git -b ${branch} && \
 cd /home/ubuntu/flashexplatform && \
 echo 'Deploying the Application' && ls && \
 docker-compose down && \
 docker-compose -f docker-compose.yml up --build -d --remove-orphans && \
 docker-compose up -d && \
 echo 'DONE DEPLOYING'"

# command="ls -ltr && \
#  cd /home/ubuntu/flashexplatform && docker-compose down && \
#  rm -rf /home/ubuntu/flashexplatform && \
#  git clone https://${gituser}:${gittoken}@gitlab.stackroute.in/flashex/flashexplatform.git -b ${branch} && \
#  cd /home/ubuntu/flashexplatform && \
#  echo 'Deploying the Application' && ls && \
#  docker-compose -f docker-compose.yml up --build -d --remove-orphans && \
#  echo 'DONE DEPLOYING'"


echo "About to run the command: " $command

ssh $user@$server $command