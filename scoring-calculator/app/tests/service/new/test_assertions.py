from datetime import datetime

from app.core.models.profile import Profile
from app.core.services.util import get_zero_if_none, calculate_dates_diff_day_count


def assert_profile(recent_p: Profile, revised_p: Profile):
    scores_dict: dict = {}

    # ----------- assert has_kyc_score -----------
    if revised_p.has_kyc:
        item_score = 40
        assert revised_p.has_kyc_score == item_score, 'Expected <has_kyc_score> is [{}] but actual is [{}]'.format(item_score, str(revised_p.has_kyc_score))
        scores_dict = update_score_values(identities_score=item_score)

    # ----------- assert military_service_status_score -----------
    if revised_p.military_service_status == 1 or revised_p.military_service_status == 2 or revised_p.military_service_status == 3:
        item_score = 20
        assert revised_p.military_service_status_score == item_score, 'Expected <military_service_status_score> is [{}] but actual is [{}]'.format(item_score, str(revised_p.military_service_status_score))
        scores_dict = update_score_values(scores_dict=scores_dict, identities_score=item_score)

    elif revised_p.military_service_status == 5:
        item_score = -50
        assert revised_p.military_service_status_score == item_score, 'Expected <military_service_status_score> is [{}] but actual is [{}]'.format(item_score, str(revised_p.military_service_status_score))
        scores_dict = update_score_values(scores_dict=scores_dict, identities_score=item_score)

    # ----------- assert sim_card_ownership_score -----------
    if revised_p.sim_card_ownership == 1:
        item_score = 20
        assert revised_p.sim_card_ownership_score == item_score, 'Expected <sim_card_ownership_score> is [{}] but actual is [{}]'.format(item_score, str(revised_p.sim_card_ownership_score))
        scores_dict = update_score_values(scores_dict=scores_dict, identities_score=item_score)

    # ----------- assert address_verification_score -----------
    if revised_p.address_verification == 1:
        item_score = 20
        assert revised_p.address_verification_score == item_score, 'Expected <address_verification_score> is [{}] but actual is [{}]'.format(item_score, str(revised_p.address_verification_score))
        scores_dict = update_score_values(scores_dict=scores_dict, identities_score=item_score)

    # ----------- assert membership_date_score -----------
    # calculate membership days count
    membership_days_count = calculate_dates_diff_day_count(revised_p.membership_date, datetime.today().date())
    if membership_days_count <= 90:
        item_score = 20
        assert revised_p.membership_date_score == item_score, 'Expected <membership_date_score> is [{}] but actual is [{}]'.format(item_score, str(revised_p.membership_date_score))
        scores_dict = update_score_values(scores_dict=scores_dict, identities_score=item_score)

    # ----------- assert membership_date_score -----------
    # calculate membership days count
    membership_days_count = calculate_dates_diff_day_count(revised_p.membership_date, datetime.today().date())
    if revised_p.recommended_to_others_count == 1:
        item_score = 10
        assert revised_p.recommended_to_others_count_score == item_score, 'Expected <recommended_to_others_count_score> is [{}] but actual is [{}]'.format(item_score, str(revised_p.recommended_to_others_count_score))
        scores_dict = update_score_values(scores_dict=scores_dict, identities_score=item_score)

    # assert p.recommended_to_others_count == 0

    # assert p.number_of_times_star_received == 0
    # assert p.number_of_times_star_received_score is None
    # assert p.star_count_average == 0
    # assert p.star_count_average_score == 0
    # assert p.identities_score == 60
    # assert p.histories_score == 11.1111111111111
    # assert p.volumes_score == 0
    # assert p.timeliness_score == 0
    # assert p.last_score_change == 71.1111111111111
    # assert p.score == 71.1111111111111

    # assert_score_values(recent_p=recent_p, revised_p=revised_p, scores_dict=scores_dict)


def update_score_values(scores_dict=None, identities_score: float = 0, histories_score: float = 0, volumes_score: float = 0, timeliness_score: float = 0) -> dict:
    if scores_dict is None:
        scores_dict = {'identities_score': 0, 'histories_score': 0, 'volumes_score': 0, 'timeliness_score': 0, 'last_score_change': 0, 'score': 0}
    else:
        scores_dict['identities_score'] += identities_score
        scores_dict['histories_score'] += histories_score
        scores_dict['volumes_score'] += volumes_score
        scores_dict['timeliness_score'] += timeliness_score
        scores_dict['last_score_change'] += identities_score + histories_score + volumes_score + timeliness_score
        scores_dict['score'] += identities_score + histories_score + volumes_score + timeliness_score
    return scores_dict


def assert_score_values(recent_p: Profile, revised_p: Profile, scores_dict: dict):
    excepted_identities_score = get_zero_if_none(recent_p.identities_score) + scores_dict['identities_score']
    assert revised_p.identities_score == excepted_identities_score, 'Expected <identities_score> is [{}] but actual is [{}]'.format(excepted_identities_score, revised_p.identities_score)
