from app.core.constants import ZERO
from app.core.data.caching.redis_caching import RedisCaching
from app.core.data.import_rules_data import import_rules
from app.core.models.cheques import Cheque
from app.core.models.done_trades import DoneTrade
from app.core.models.loans import Loan
from app.core.models.profile import Profile
from app.core.models.undone_trades import UndoneTrade
from app.core.services.data_service import DataService
from app.core.services.score_calculation_handler import ScoreCalculationHandler
from app.tests.service.new.test_database import get_test_db, dropping_test_db, dropping_specified_collections

ds = DataService(get_test_db())
sch = ScoreCalculationHandler(ds=ds)


def get_data_service_for_test_db():
    return ds


def get_score_calculation_handler_for_test_db():
    return sch


def drop_test_db(request):
    if request.config.getoption("--drop-test-db") == 'true':
        dropping_test_db()
        print('\n!!test is ran on a clean test db!!')
        import_rules(ds)


def drop_test_collections(request):
    if request.config.getoption("--drop-test-collections") == 'true':
        dropping_specified_collections('profiles', 'loans', 'cheques', 'undoneTrades', 'doneTrades', 'scoreChanges')


def redis_caching(request):
    if request.config.getoption("--cache-rules") == 'true':
        RedisCaching(ds).cache_rules()


# noinspection DuplicatedCode
def create_and_recalculate_profile_score(recent_p: Profile = None,
                                         user_id=None,
                                         has_kyc=None,
                                         military_service_status=None,
                                         sim_card_ownership=None,
                                         address_verification=None,
                                         membership_date=None,
                                         recommended_to_others_count=None,
                                         number_of_times_star_received=None,
                                         star_count_average=None,
                                         identities_score=None,
                                         histories_score=None,
                                         volumes_score=None,
                                         timeliness_score=None
                                         ):
    # load user profile
    if recent_p is None:
        recent_p: Profile = ds.get_user_profile(user_id=user_id, null_safe=False)
    revised_p = Profile(user_id=user_id,
                        has_kyc=has_kyc,
                        military_service_status=military_service_status,
                        sim_card_ownership=sim_card_ownership,
                        address_verification=address_verification,
                        membership_date=membership_date,
                        recommended_to_others_count=recommended_to_others_count,
                        number_of_times_star_received=number_of_times_star_received,
                        star_count_average=star_count_average,
                        identities_score=identities_score,
                        histories_score=histories_score,
                        volumes_score=volumes_score,
                        timeliness_scor=timeliness_score
                        )
    if recent_p.user_id is not None:
        # set all none fields of revised_profile by recent_profile fields
        revised_p.has_kyc = has_kyc if has_kyc is not None else recent_p.has_kyc
        revised_p.has_kyc_score = recent_p.has_kyc_score

        revised_p.military_service_status = military_service_status if military_service_status is not None else recent_p.military_service_status
        revised_p.military_service_status_score = recent_p.military_service_status_score

        revised_p.sim_card_ownership = sim_card_ownership if sim_card_ownership is not None else recent_p.sim_card_ownership
        revised_p.sim_card_ownership_score = recent_p.sim_card_ownership_score

        revised_p.address_verification = address_verification if address_verification is not None else recent_p.address_verification
        revised_p.address_verification_score = recent_p.address_verification_score

        revised_p.membership_date = membership_date if membership_date is not None else recent_p.membership_date
        revised_p.membership_date_score = recent_p.membership_date_score

        revised_p.recommended_to_others_count = recommended_to_others_count if recommended_to_others_count is not None else recent_p.recommended_to_others_count
        revised_p.recommended_to_others_count_score = recent_p.recommended_to_others_count_score

        revised_p.number_of_times_star_received = number_of_times_star_received if number_of_times_star_received is not None else recent_p.number_of_times_star_received
        revised_p.number_of_times_star_received_score = recent_p.number_of_times_star_received_score

        revised_p.star_count_average = star_count_average if star_count_average is not None else recent_p.star_count_average
        revised_p.star_count_average_score = recent_p.star_count_average_score

        revised_p.identities_score = identities_score if identities_score is not None else recent_p.identities_score
        revised_p.histories_score = histories_score if histories_score is not None else recent_p.histories_score
        revised_p.volumes_score = volumes_score if volumes_score is not None else recent_p.volumes_score
        revised_p.timeliness_score = timeliness_score if timeliness_score is not None else recent_p.timeliness_score
        revised_p.score = recent_p.score

    # set default value for score fields
    revised_p.identities_score = revised_p.identities_score if revised_p.identities_score is not None else ZERO
    revised_p.histories_score = revised_p.histories_score if revised_p.histories_score is not None else ZERO
    revised_p.volumes_score = revised_p.volumes_score if revised_p.volumes_score is not None else ZERO
    revised_p.timeliness_score = revised_p.timeliness_score if revised_p.timeliness_score is not None else ZERO

    # calculate score
    sch.calculate_score_by_revised_profile(recent_p=recent_p, revised_p=revised_p)


def create_and_recalculate_loan_score(user_id=None,
                                      loans_total_count=None,
                                      loans_total_balance=None,
                                      past_due_loans_total_count=None,
                                      arrear_loans_total_count=None,
                                      suspicious_loans_total_count=None,
                                      monthly_installments_total_balance=None,
                                      overdue_loans_total_balance=None,
                                      past_due_loans_total_balance=None,
                                      arrear_loans_total_balance=None,
                                      suspicious_loans_total_balance=None):
    revised_ln = Loan(user_id=user_id,
                      loans_total_count=loans_total_count,
                      loans_total_balance=loans_total_balance,
                      past_due_loans_total_count=past_due_loans_total_count,
                      arrear_loans_total_count=arrear_loans_total_count,
                      suspicious_loans_total_count=suspicious_loans_total_count,
                      monthly_installments_total_balance=monthly_installments_total_balance,
                      overdue_loans_total_balance=overdue_loans_total_balance,
                      past_due_loans_total_balance=past_due_loans_total_balance,
                      arrear_loans_total_balance=arrear_loans_total_balance,
                      suspicious_loans_total_balance=suspicious_loans_total_balance
                      )
    sch.calculate_score_by_revised_loan(revised_ln=revised_ln)


def create_and_recalculate_cheque_score(user_id=None,
                                        unfixed_returned_cheques_count_of_last_3_months=None,
                                        unfixed_returned_cheques_count_between_last_3_to_12_months=None,
                                        unfixed_returned_cheques_count_of_more_12_months=None,
                                        unfixed_returned_cheques_count_of_last_5_years=None,
                                        unfixed_returned_cheques_total_balance=None):
    revised_ch = Cheque(user_id=user_id,
                        unfixed_returned_cheques_count_of_last_3_months=unfixed_returned_cheques_count_of_last_3_months,
                        unfixed_returned_cheques_count_between_last_3_to_12_months=unfixed_returned_cheques_count_between_last_3_to_12_months,
                        unfixed_returned_cheques_count_of_more_12_months=unfixed_returned_cheques_count_of_more_12_months,
                        unfixed_returned_cheques_count_of_last_5_years=unfixed_returned_cheques_count_of_last_5_years,
                        unfixed_returned_cheques_total_balance=unfixed_returned_cheques_total_balance
                        )
    sch.calculate_score_by_revised_cheque(revised_ch=revised_ch)


def create_and_calculate_score_by_revised_undone_trade(user_id=None,
                                                       calculation_start_date=None,
                                                       undue_trades_count=None,
                                                       past_due_trades_count=None,
                                                       arrear_trades_count=None,
                                                       undue_trades_total_balance_of_last_year=None,
                                                       past_due_trades_total_balance_of_last_year=None,
                                                       arrear_trades_total_balance_of_last_year=None):
    revised_udt = UndoneTrade(user_id=user_id,
                              calculation_start_date=calculation_start_date,
                              undue_trades_count=undue_trades_count,
                              past_due_trades_count=past_due_trades_count,
                              arrear_trades_count=arrear_trades_count,
                              undue_trades_total_balance_of_last_year=undue_trades_total_balance_of_last_year,
                              past_due_trades_total_balance_of_last_year=past_due_trades_total_balance_of_last_year,
                              arrear_trades_total_balance_of_last_year=arrear_trades_total_balance_of_last_year
                              )

    sch.calculate_score_by_revised_undone_trade(revised_udt=revised_udt)


def create_and_calculate_score_by_revised_done_trade(user_id=None,
                                                     calculation_start_date=None,
                                                     timely_trades_count_of_last_3_months=None,
                                                     past_due_trades_count_of_last_3_months=None,
                                                     arrear_trades_count_of_last_3_months=None,
                                                     timely_trades_count_between_last_3_to_12_months=None,
                                                     past_due_trades_count_between_last_3_to_12_months=None,
                                                     arrear_trades_count_between_last_3_to_12_months=None,
                                                     trades_total_balance=None,
                                                     total_delay_days=None):
    revised_dt = DoneTrade(
        user_id=user_id,
        calculation_start_date=calculation_start_date,
        timely_trades_count_of_last_3_months=timely_trades_count_of_last_3_months,
        past_due_trades_count_of_last_3_months=past_due_trades_count_of_last_3_months,
        arrear_trades_count_of_last_3_months=arrear_trades_count_of_last_3_months,
        timely_trades_count_between_last_3_to_12_months=timely_trades_count_between_last_3_to_12_months,
        past_due_trades_count_between_last_3_to_12_months=past_due_trades_count_between_last_3_to_12_months,
        arrear_trades_count_between_last_3_to_12_months=arrear_trades_count_between_last_3_to_12_months,
        trades_total_balance=trades_total_balance,
        total_delay_days=total_delay_days
    )

    sch.calculate_score_by_revised_done_trade(revised_dt=revised_dt)
