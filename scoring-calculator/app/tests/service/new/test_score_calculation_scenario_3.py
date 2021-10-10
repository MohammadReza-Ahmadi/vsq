from datetime import datetime, timedelta

from app.core.models.scoring_enums import ProfileMilitaryServiceStatusEnum
from app.tests.service.new.test_assertions import assert_profile
from app.tests.service.new.test_score_calculation_util import drop_test_db, drop_test_collections, redis_caching, create_and_recalculate_profile_score, get_data_service_for_test_db

user_id = 2
user_membership_date = datetime.today() - timedelta(days=10)
user_calculation_start_date = datetime.today() - timedelta(days=5)


# noinspection DuplicatedCode
def test_drop_test_db(request):
    drop_test_db(request)


def test_drop_collections(request):
    drop_test_collections(request)


def test_redis_caching(request):
    redis_caching(request)


def test_scenario_1(request):
    # load recent user profile
    recent_p = get_data_service_for_test_db().get_user_profile(user_id=user_id, null_safe=False)
    # create a profile
    create_and_recalculate_profile_score(recent_p=recent_p,
                                         user_id=user_id,
                                         military_service_status=ProfileMilitaryServiceStatusEnum.FINISHED,
                                         has_kyc=True,
                                         sim_card_ownership=False,
                                         address_verification=False,
                                         membership_date=user_membership_date,
                                         recommended_to_others_count=0,
                                         number_of_times_star_received=0,
                                         star_count_average=0,
                                         identities_score=0,
                                         histories_score=0,
                                         volumes_score=0,
                                         timeliness_score=0
                                         )
    # assert calculation result
    revised_p = get_data_service_for_test_db().get_user_profile(user_id=user_id)
    assert_profile(recent_p=recent_p, revised_p=revised_p)

    # UDT-1:  create first timely undone_trade by amount 10000000
    # create_and_calculate_score_by_revised_undone_trade(user_id=user_id, calculation_start_date=user_calculation_start_date, undue_trades_count=1, undue_trades_total_balance_of_last_year=10000000)
    # udt = get_data_service_for_test_db().get_user_undone_trade(user_id=user_id)
    # print(udt)
    # assert udt.undue_trades_count == 1
    # assert udt.undue_trades_count_score == 2.2222222222222
    # assert udt.past_due_trades_count is None
    # assert udt.past_due_trades_count_score == 12.962962962963
    # assert udt.arrear_trades_count is None
    # assert udt.arrear_trades_count_score == 25.9259259259259
    # assert udt.undue_trades_total_balance_of_last_year == 10000000
    # assert udt.undue_trades_total_balance_of_last_year_score == 0
    # assert udt.past_due_trades_total_balance_of_last_year is None
    # assert udt.past_due_trades_total_balance_of_last_year_score == 25.6410256410256
    # assert udt.arrear_trades_total_balance_of_last_year is None
    # assert udt.arrear_trades_total_balance_of_last_year_score == 38.4615384615385

    # # create done_trade:1
    # create_and_calculate_score_by_revised_done_trade(user_id=user_id, calculation_start_date=user_calculation_start_date, timely_trades_count_of_last_3_months=1, trades_total_balance=20000000)
