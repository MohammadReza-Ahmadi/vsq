#!/bin/bash

# remove old container
docker rm scoring-calculator
echo "scoring-calculator container is removed"
echo " "

# remove old image
docker image rm scoring-calculator-img:latest
echo "scoring-calculator-img image is removed"
echo " "

# build new image
docker build -t scoring-calculator-img .
echo "scoring-calculator-img image is built"
echo " "

# run new container
#docker run --name scoring-calculator -p8016:8016 scoring-calculator-img
#docker run --name scoring-calculator -p8016:8016 -e "mongodb_host=172.17.0.1"
#echo 'scoring-calculator container is run'



# At the command line, run: chmod u+x <YourScriptFileName.sh>
# run script by command: ./deploy.sh