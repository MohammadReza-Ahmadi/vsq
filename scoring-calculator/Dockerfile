# pull the official docker image
FROM python:3.8-alpine

# copy the local requirements.txt file to the /app/requirements.txt in the container (the /app dir will be created)
COPY ./requirements.txt /app/requirements.txt

# install the packages from the requirements.txt file in the container
RUN pip3 install -r /app/requirements.txt

# expose the port that uvicorn will run the app
EXPOSE 8016

# copy the local app/ folder to the /app fodler in the container
COPY ./app /app

# add project path to PYTHONPATH environment variable
ENV PYTHONPATH "${PYTHONPATH}:/"

# add mongodb-host address as environment variable
ENV mongodb_host="172.17.0.1"

# set the working directory in the container to be the /app
WORKDIR /app

# execute the command python main.py (in the WORKDIR) to start the app
#CMD ["python3", "main.py"]
CMD ["python3", "core/services/kafka_undone_trades_consumer_service.py"]