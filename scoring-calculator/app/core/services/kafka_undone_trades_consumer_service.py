import json

from kafka import KafkaConsumer

from app.core.models.undone_trades import UndoneTrade
from app.core.services.score_calculation_handler import ScoreCalculationHandler
from app.core.settings import kafka_bootstrap_servers, kafka_undone_trades_topic, kafka_topics_enabled, kafka_undone_trades_group


# noinspection PyMethodMayBeStatic,DuplicatedCode
class KafkaUndoneTradesConsumerService:
    scoreCalculationHandler: ScoreCalculationHandler = None

    def __init__(self):
        super().__init__()

    def run_consumer(self):
        self.scoreCalculationHandler = ScoreCalculationHandler()
        print('KafkaUndoneTradesConsumerService is running on bootstrap-server:[{}] for consume [{}] messages...'.format(kafka_bootstrap_servers, kafka_undone_trades_topic))

        # make undone trade kafka consumer
        undone_trades_consumer = KafkaConsumer(kafka_undone_trades_topic,
                                               bootstrap_servers=kafka_bootstrap_servers,
                                               auto_offset_reset='latest',
                                               enable_auto_commit=True,
                                               group_id=kafka_undone_trades_group
                                               )
        for message in undone_trades_consumer:
            try:
                self.consume_undone_trades_payloads(message)
            except Exception as e:
                print("KafkaUndoneTradesConsumerService encountered an exception when read Kafka message:[{}] ".format(e))

    # undone_trades kafka consumer
    def consume_undone_trades_payloads(self, message):
        print("KafkaUndoneTradesConsumerService is processing new message ->")
        print('message:[{}]'.format(message))
        # convert kafka topic undone_trade json info to undone_trade object
        revised_udt: UndoneTrade = json.loads(message.value, object_hook=lambda d: UndoneTrade(**d))

        print('start score calculation based on received data:\nundoneTrade:[{}]'.format(revised_udt))
        self.scoreCalculationHandler.calculate_score_by_revised_undone_trade(revised_udt=revised_udt)
        print("KafkaUndoneTradesConsumerService has processed the new message successfully.\n")


if __name__ == '__main__':
    if bool(kafka_topics_enabled):
        KafkaUndoneTradesConsumerService().run_consumer()
