import json

from kafka import KafkaConsumer

from app.core.models.done_trades import DoneTrade
from app.core.services.score_calculation_handler import ScoreCalculationHandler
from app.core.settings import kafka_bootstrap_servers, kafka_topics_enabled, kafka_done_trades_topic, kafka_done_trades_group, kafka_undone_trades_topic


# noinspection PyMethodMayBeStatic,DuplicatedCode
class KafkaDoneTradesConsumerService:
    scoreCalculationHandler: ScoreCalculationHandler = None

    def __init__(self):
        super().__init__()

    def run_consumer(self):
        # logging.basicConfig(level=logging.INFO)
        # logger = logging.getLogger()
        self.scoreCalculationHandler = ScoreCalculationHandler()
        print('KafkaUndoneTradesConsumerService is running on bootstrap-server:[{}] for consume [{}] messages...'.format(kafka_bootstrap_servers, kafka_undone_trades_topic))

        # make undone trade kafka consumer
        done_trades_consumer = KafkaConsumer(kafka_done_trades_topic,
                                             bootstrap_servers=kafka_bootstrap_servers,
                                             # auto_offset_reset='earliest',
                                             auto_offset_reset='latest',
                                             enable_auto_commit=True,
                                             # auto_commit_interval_ms=60000,
                                             group_id=kafka_done_trades_group
                                             )
        for message in done_trades_consumer:
            try:
                self.consume_done_trades_payloads(message)
            except Exception as e:
                print("KafkaUndoneTradesConsumerService encountered an exception when read Kafka message:[{}] ".format(e))
                # undone_trades_consumer.commit()
                # undone_trades_consumer.close()
                # print("undone_trades_consumer is closed!")
            # finally:

    # undone_trades kafka consumer
    def consume_done_trades_payloads(self, message):
        print("KafkaDoneTradesConsumerService is processing new message ->")
        print('message:[{}]'.format(message))
        # convert kafka topic done_trade json info to done_trade object
        revised_dt: DoneTrade = json.loads(message.value, object_hook=lambda d: DoneTrade(**d))
        print('start score calculation based on received data:\ndoneTrade:[{}]'.format(revised_dt))
        self.scoreCalculationHandler.calculate_score_by_revised_done_trade(revised_dt=revised_dt)
        print("KafkaDoneTradesConsumerService has processed the new message successfully.\n")


if __name__ == '__main__':
    if bool(kafka_topics_enabled):
        KafkaDoneTradesConsumerService().run_consumer()
