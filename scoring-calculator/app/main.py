import threading

import uvicorn

from app.core.settings import fastapi_url, fastapi_port, kafka_topics_enabled


# launch fastApi
def launch_fast_api():
    uvicorn.run('core.app:app', host=fastapi_url, port=fastapi_port, reload=True)
    print("uvicorn server is running.")


if __name__ == '__main__':
    # threading.Timer(3.0, launch_kafka).start()
    launch_fast_api()
