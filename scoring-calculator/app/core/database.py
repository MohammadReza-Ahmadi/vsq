import os

from pymongo import MongoClient

from app.core.settings import mongodb_user, mongodb_pass, mongodb_port, mongodb_name, mongodb_host


def get_db():
    mongodb_host_var = mongodb_host
    if os.environ.get('mongodb_host') is not None:
        mongodb_host_var = os.environ['mongodb_host']

    client = MongoClient('mongodb://' + mongodb_user + ':' + mongodb_pass + '@' + mongodb_host_var + ':' + str(mongodb_port))
    return client.get_database(mongodb_name)
