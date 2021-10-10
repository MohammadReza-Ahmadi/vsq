# noinspection DuplicatedCode
import os

from pymongo import MongoClient

from app.core.settings import mongodb_test_user, mongodb_test_pass, mongodb_test_port, mongodb_test_name, mongodb_test_host


def get_test_db():
    client = get_mongo_client()
    return client.get_database(mongodb_test_name)


def get_mongo_client():
    mongodb_test_host_var = mongodb_test_host
    if os.environ.get('mongodb_host') is not None:
        mongodb_test_host_var = os.environ['mongodb_host']
    client = MongoClient('mongodb://' + mongodb_test_user + ':' + mongodb_test_pass + '@' + mongodb_test_host_var + ':' + str(mongodb_test_port))
    return client


def dropping_test_db():
    get_mongo_client().drop_database(mongodb_test_name)


def dropping_specified_collections(*collection_names):
    print('\n')
    for c in collection_names:
        get_test_db().drop_collection(c)
        print('!!!!!!!! [{}] collection is dropped !!!!!!!!'.format(c))
