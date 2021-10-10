from datetime import datetime, timedelta

from app.core.models.scoring_enums import ProfileMilitaryServiceStatusEnum
from app.tests.service.new.test_score_calculation_util import drop_test_db, create_and_recalculate_loan_score, create_and_recalculate_cheque_score, create_and_recalculate_profile_score, create_and_calculate_score_by_revised_undone_trade, create_and_calculate_score_by_revised_done_trade, \
    redis_caching

user_id = 1
user_membership_date = datetime.today() - timedelta(days=89)
user_calculation_start_date = datetime.today() - timedelta(days=88)


def test_drop_test_db(request):
    drop_test_db(request)


def test_redis_caching(request):
    redis_caching(request)


def test_scenario_1():
    # create a profile
    create_and_recalculate_profile_score(user_id, military_service_status=ProfileMilitaryServiceStatusEnum.FINISHED,
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
    # create default loan data
    create_and_recalculate_loan_score(user_id)
    # create default cheque data
    create_and_recalculate_cheque_score(user_id)
    # create undone_trade:1
    create_and_calculate_score_by_revised_undone_trade(user_id=user_id, calculation_start_date=user_calculation_start_date, undue_trades_count=1, undue_trades_total_balance_of_last_year=20000000)
    # create done_trade:1
    create_and_calculate_score_by_revised_done_trade(user_id=user_id, calculation_start_date=user_calculation_start_date, timely_trades_count_of_last_3_months=1, trades_total_balance=20000000)
    # create undone_trade:2
    create_and_calculate_score_by_revised_undone_trade(user_id=user_id, undue_trades_count=2, undue_trades_total_balance_of_last_year=15000000)
    # create done_trade:2
    create_and_calculate_score_by_revised_done_trade(user_id=user_id, timely_trades_count_of_last_3_months=2, trades_total_balance=20000000 + 15000000)
    # create undone_trade:3
    create_and_calculate_score_by_revised_undone_trade(user_id=user_id, undue_trades_count=3, undue_trades_total_balance_of_last_year=45000000)
    # create done_trade:3
    create_and_calculate_score_by_revised_done_trade(user_id=user_id, timely_trades_count_of_last_3_months=3, trades_total_balance=20000000 + 15000000 + 45000000)
    # update loan data from central bank
    create_and_recalculate_loan_score(user_id, loans_total_count=1, loans_total_balance=700000000)
    # update cheque data from central bank
    create_and_recalculate_cheque_score(user_id, unfixed_returned_cheques_count_of_last_3_months=1, unfixed_returned_cheques_total_balance=100000000)
    # update profile by verify sic_card ownership
    create_and_recalculate_profile_score(user_id=user_id, sim_card_ownership=True)
    # update profile by verify address
    create_and_recalculate_profile_score(user_id=user_id, address_verification=True)
    # update loan data from central bank
    create_and_recalculate_loan_score(user_id, loans_total_count=2, loans_total_balance=1400000000, past_due_loans_total_count=1, past_due_loans_total_balance=700000000)
