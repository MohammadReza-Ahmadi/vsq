from kafka import KafkaConsumer
import json
from types import SimpleNamespace

from app.core.models.undone_trades import UndoneTrade

if __name__ == '__main__':
    # consumer = KafkaConsumer('contractsTopic', auto_offset_reset='earliest', group_id=None)
    consumer = KafkaConsumer('undoneTradesTopic', bootstrap_servers=['localhost:9092'],
                             auto_offset_reset='earliest',
                             # enable_auto_commit=True,
                             # auto_commit_interval_ms=1000, group_id=None
                             )
    print("Consuming messages")

    # test json
    data = '{"name": "John Smith", "hometown": {"name": "New York", "id": 123}}'
    # Parse JSON into an object with attributes corresponding to dict keys.
    x = json.loads(data, object_hook=lambda d: SimpleNamespace(**d))
    print(x.name, x.hometown.name, x.hometown.id)
    for msg in consumer:
        x: UndoneTrade = json.loads(msg.value, object_hook=lambda d: SimpleNamespace(**d))
        print(x.undueTradesCount)
        print(x.undue_trades_count)
