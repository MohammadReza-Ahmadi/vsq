# FastApi config
fastapi_url = '0.0.0.0'
fastapi_port = 8016
service_message_code = '009'

# MongoDB config
mongodb_host = '127.0.0.1'
mongodb_port = 27017
mongodb_user = 'admin'
mongodb_pass = 'admin'
mongodb_auth_source = 'admin'
mongodb_name = 'scoring-calculator'
mongodb_alias = 'scoring-calculator'


# MongoDB test config
mongodb_test_host = '127.0.0.1'
mongodb_test_port = 27017
mongodb_test_user = 'admin'
mongodb_test_pass = 'admin'
mongodb_test_auth_source = 'admin'
mongodb_test_name = 'test-scoring-calculator'
mongodb_test_alias = 'test-scoring-calculator'


# Kafka config
kafka_topics_enabled = True
kafka_bootstrap_servers = 'localhost:9092'

# Kafka topic config
kafka_undone_trades_topic = 'undoneTradesTopic'
kafka_done_trades_topic = 'doneTradesTopic'

# kafka group config
kafka_undone_trades_group = 'undoneTradesGroup'
kafka_done_trades_group = 'doneTradesGroup'

# Redis config
redis_host = '127.0.0.1'
redis_port = 6379
redis_pass = ''
redis_reset = False

# Import rules data config
import_rules_flag = True

# Scoring Config
min_score = 0
config_max_score = 1000
distribution_count = 30
initial_score_change_store_config = True
# NUMBER_OF_DECIMAL_PLACES = -1
NUMBER_OF_DECIMAL_PLACES = 13
# max long value in java: 9223372036854775807
