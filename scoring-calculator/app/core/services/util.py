import random
import string
from datetime import datetime, timedelta
from typing import List

import jdatetime
from mongoengine.queryset.visitor import Q

from app.core.constants import score_deliminator, NORMALIZATION_MAX_SCORE, ONE_HUNDRED, SCORE, CODE, MAX, YEARS, ZERO, \
    DAYS, MONTHS, ONE
from app.core.models.cheques import Cheque
from app.core.models.dtos.cheque_status_dto import ChequesStatusDTO
from app.core.models.dtos.loan_status_dto import LoansStatusDTO
from app.core.models.dtos.score_change_dto import ScoreChangeDTO
from app.core.models.dtos.score_details_dto import ScoreDetailsDTO
from app.core.models.dtos.score_status_dto import ScoreStatusDTO
from app.core.models.dtos.vosouq_status_dto import VosouqStatusDTO
from app.core.models.loans import Loan
from app.core.models.profile import Profile
from app.core.models.rules import Rule
from app.core.models.score_changes import ScoreChange
# noinspection DuplicatedCode
from app.core.models.score_reasons import ScoreReason
# noinspection DuplicatedCode
from app.core.settings import config_max_score


def create_revised_profile(user_id: int, recent_p: Profile):
    p = Profile()
    p.user_id = user_id
    p.identities_score = recent_p.identities_score
    p.histories_score = recent_p.histories_score
    p.timeliness_score = recent_p.timeliness_score
    p.volumes_score = recent_p.volumes_score
    p.score = recent_p.score
    return p


def create_new_rule(level, parent: str, code: str, title: str, impact_percent: float, score: int = None, min_val: float = None, max_val: float = None):
    rule = Rule()
    rule.level = level
    rule.parent = parent
    rule.code = code
    rule.title = title
    rule.impact_percent = impact_percent
    rule.score = score
    rule.min = min_val
    rule.max = min_val if max_val is None else max_val
    return rule


def create_new_score_reason(rule_master_code: str, rule_codes: List[str], positive_reason: str, negative_reason: str = None):
    scr = ScoreReason()
    scr.rule_master_code = rule_master_code
    scr.rule_codes = rule_codes
    scr.positive_reason = positive_reason
    scr.negative_reason = negative_reason
    return scr


def create_new_score_change(user_id: int, reason_rule_code: str, score_change: int, score: int):
    sch = ScoreChange()
    sch.user_id = user_id
    sch.reason_rule_code = reason_rule_code
    sch.score_change = score_change
    sch.score = score
    sch.change_date = datetime.today()
    return sch


def create_score_status_dto(p: Profile, score_gauges: list):
    ssd = ScoreStatusDTO()
    ssd.score = p.score
    ssd.max_score = config_max_score
    ssd.last_score_change = p.last_score_change
    ssd.last_update_date = p.last_update_date
    ssd.score_gauges = score_gauges
    return ssd


def create_vosouq_status_dto(
        membership_duration_day: int,
        membership_duration_month: int,
        done_trades_count: int,
        undone_trades_count: int,
        negative_status_count: int,
        delay_days_count_avg: int,
        recommend_to_others_count: int
) -> VosouqStatusDTO:
    vsd = VosouqStatusDTO()
    vsd.membership_duration_day = get_not_none_value(membership_duration_day, vsd.membership_duration_day)
    vsd.membership_duration_month = get_not_none_value(membership_duration_month, vsd.membership_duration_month)
    vsd.done_trades_count = get_not_none_value(done_trades_count, vsd.done_trades_count)
    vsd.undone_trades_count = get_not_none_value(undone_trades_count, vsd.undone_trades_count)
    vsd.negative_status_count = get_not_none_value(negative_status_count, vsd.negative_status_count)
    vsd.delay_days_count_avg = get_not_none_value(delay_days_count_avg, vsd.delay_days_count_avg)
    vsd.recommend_to_others_count = get_not_none_value(recommend_to_others_count, vsd.recommend_to_others_count)
    return vsd


def create_loan_status_dto(ln: Loan) -> LoansStatusDTO:
    lsd = LoansStatusDTO()
    lsd.current_loans_count = get_not_none_value(ln.loans_total_count, lsd.current_loans_count)
    lsd.past_due_loans_amount = get_not_none_value(ln.past_due_loans_total_balance, lsd.past_due_loans_amount)
    lsd.arrears_loans_amount = get_not_none_value(ln.arrear_loans_total_balance, lsd.arrears_loans_amount)
    lsd.suspicious_loans_amount = get_not_none_value(ln.suspicious_loans_total_balance, lsd.suspicious_loans_amount)
    return lsd


# noinspection DuplicatedCode
def create_cheque_status_dto(ch: Cheque) -> ChequesStatusDTO:
    csd = ChequesStatusDTO()
    csd.unfixed_returned_cheques_count_of_last_3_months = get_not_none_value(
        ch.unfixed_returned_cheques_count_of_last_3_months,
        csd.unfixed_returned_cheques_count_of_last_3_months)
    csd.unfixed_returned_cheques_count_between_last_3_to_12_months = get_not_none_value(
        ch.unfixed_returned_cheques_count_between_last_3_to_12_months,
        csd.unfixed_returned_cheques_count_between_last_3_to_12_months)
    csd.unfixed_returned_cheques_count_of_more_12_months = get_not_none_value(
        ch.unfixed_returned_cheques_count_of_more_12_months,
        csd.unfixed_returned_cheques_count_of_more_12_months)
    csd.unfixed_returned_cheques_count_of_last_5_years = get_not_none_value(
        ch.unfixed_returned_cheques_count_of_last_5_years,
        csd.unfixed_returned_cheques_count_of_last_5_years)
    csd.unfixed_returned_cheques_total_balance = get_not_none_value(
        ch.unfixed_returned_cheques_total_balance,
        csd.unfixed_returned_cheques_total_balance)

    return csd


# noinspection DuplicatedCode
def create_score_details_dto(pf: Profile) -> ScoreDetailsDTO:
    sdd = ScoreDetailsDTO()
    sdd.score = get_not_none_value(pf.score, sdd.score)
    sdd.identities_score = get_not_none_value(pf.identities_score, sdd.identities_score)
    sdd.histories_score = get_not_none_value(pf.histories_score, sdd.histories_score)
    sdd.volumes_score = get_not_none_value(pf.volumes_score, sdd.volumes_score)
    sdd.timeliness_score = get_not_none_value(pf.timeliness_score, sdd.timeliness_score)
    return sdd


def create_score_changes_dto(sch: ScoreChange) -> ScoreChangeDTO:
    scd: ScoreChangeDTO = ScoreChangeDTO()
    scd.change_reason = sch.reason_desc
    scd.change_date = sch.change_date
    scd.score_change = sch.score_change
    return scd


def calculate_dates_diff_by_day(start: datetime, end: datetime) -> int:
    if end is None or start is None or end <= start:
        return ZERO
    d = (end - start)
    return d.days


def calculate_dates_diff_by_months_and_days(start: datetime, end: datetime) -> {}:
    if end is None or start is None or end <= start:
        return {YEARS: ZERO, MONTHS: ZERO, DAYS: ZERO}
    d = (end - start)
    mns = d.days // 30
    dys = d.days - (mns * 30)
    return {MONTHS: mns, DAYS: dys}


def calculate_dates_diff(start: datetime, end: datetime) -> {}:
    if end is None or start is None or end <= start:
        return {YEARS: ZERO, MONTHS: ZERO, DAYS: ZERO}
    d = (end - start)
    yrs = d.days // 365
    dys = d.days - (yrs * 365)
    mns = dys // 30
    dys = dys - (mns * 30)
    return {YEARS: yrs, MONTHS: mns, DAYS: dys}

    # if d.days < 30:
    #     return DateDiff(days=d.days)
    # if d.days < 365:
    #     mns = (d.days // 30)
    #     dys = (d.days - (mns * 30))
    #     return DateDiff(days=dys, months=mns)
    #
    # yrs = (d.days // 365)
    # rm = (d.days - (yrs * 365))


def calculate_dates_diff_day_count(start_date, end_date):
    return (end_date - start_date).days


def create_new_rule2(rule: Rule, level, parent: str, code: str, title: str, impact_percent: float, score: int = None,
                     min_val: float = None,
                     max_val: float = None):
    rule.level = level
    rule.parent = parent
    rule.code = code
    rule.title = title
    rule.impact_percent = impact_percent
    rule.score = score
    rule.min = min_val
    rule.max = min_val if max_val is None else max_val
    return rule


def get_score_from_dict(scores: {}):
    return int(scores[0].split(score_deliminator)[0])


def get_max_score_from_dict(scores: {}):
    return int(scores[len(scores) - 1].split(score_deliminator)[0])


def get_score_code_from_dict(scores: {}):
    return scores[0].split(score_deliminator)[1]


def add_rule_to_dict(rdict: {}, r: Rule):
    # rdict.__setitem__((str(r.score) + score_deliminator + r.code), r.max)
    rdict.__setitem__((str(r[SCORE]) + score_deliminator + r[CODE]), r[MAX])
    return rdict


def add_rule_to_dict2(rdict: {}, r: Rule):
    rdict.__setitem__((str(r.score) + score_deliminator + r.code), r.max)
    return rdict


def calculate_normalized_score(parent_code: str, score: int):
    rules: [Rule] = Rule.objects(Q(parent=parent_code, score=score))
    # print(rules[0].impact_percent)
    percent = rules[0].impact_percent
    # normalized_score = round(percent * NORMALIZATION_MAX_SCORE / ONE_HUNDRED)
    normalized_score = (percent * NORMALIZATION_MAX_SCORE / ONE_HUNDRED)
    if score < 0:
        normalized_score = (normalized_score * -1)
    print('\nnormalized_score= {} , percent= {}'.format(normalized_score, percent))
    return normalized_score


def add_item_to_dict(rdict: {}, key, value):
    rdict.__setitem__(key, value)
    return rdict


def filter_dict(dic: dict, filtered_item) -> dict:
    nd = {}
    for key, value in dic.items():
        if key != filtered_item:
            nd[key] = value
    return nd


def filter_dict_by_id(dic) -> dict:
    return filter_dict(dic, "_id")


# Random Generator Functions #
# generate random lowercase str by fix length
def get_random_lowercase_str(str_len):
    letters = string.ascii_lowercase
    # print(''.join(random.choice(letters) for i in range(10)))
    rand_str = random_choice(letters, str_len)
    return rand_str


# generate random uppercase str by fix length
def get_random_uppercase_str(str_len):
    letters = string.ascii_uppercase
    rand_str = random_choice(letters, str_len)
    return rand_str


# generate random letters str by fix length
def get_random_letters_str(str_len):
    letters = string.ascii_letters
    rand_str = random_choice(letters, str_len)
    return rand_str


# generate random digits str by fix length
def get_random_digits_str(str_len):
    letters = string.digits
    rand_digits = random_choice(letters, str_len)
    return rand_digits


# generate random punctuation str by fix length
def get_random_punctuation_str(str_len):
    letters = string.punctuation
    rand_str = random_choice(letters, str_len)
    return rand_str


def random_choice(letters, str_len):
    return ''.join(random.choice(letters) for i in range(str_len))


def get_not_none_value(obj, default_val):
    return obj if obj is not None else default_val


def is_none(val):
    return not bool(val)


def is_not_none(val):
    return not is_none(val)


def is_none_or_zero_int(val: int):
    return not bool(val) or val == ZERO


def is_none_or_zero_float(val: float):
    return not bool(val) or val == ZERO


def is_not_none(val) -> bool:
    return bool(val)


def get_zero_if_none(num):
    return ZERO if num is None else num


def get_false_if_none(val):
    return False if val is None else val


def get_first_item(lst: list):
    if len(lst) > ZERO:
        return lst[ZERO]
    return None


def get_second_item(lst: list):
    if len(lst) > 1:
        return lst[ONE]
    return None


def get_today_date(persian: bool = False):
    if persian:
        return jdatetime.date.today()
    return jdatetime.date.today().togregorian()


def get_first_date_of_current_month(persian: bool = False):
    td = get_today_date()
    d = jdatetime.timedelta(days=td.day - ONE)
    if persian:
        return td.__sub__(d)
    return td.__sub__(d).togregorian()


def get_first_date_of_month_in_specified_months_ago(num_of_month_ago: int, persian: bool = False):
    if num_of_month_ago == 1:
        return get_first_date_of_current_month()

    td = get_today_date()
    for m in range(num_of_month_ago):
        dlt = jdatetime.timedelta(days=td.day - ONE)
        td = td.__sub__(dlt)

    if persian:
        return td
    return td.togregorian()


def get_last_date_of_previous_month(persian: bool = False):
    td = get_today_date()
    dlt = jdatetime.timedelta(days=td.day)
    if persian:
        return td.__sub__(dlt)
    return td.__sub__(dlt).togregorian()


def convert_date_to_milliseconds_since_epoch(date_time: datetime):
    return int(round(date_time.timestamp() * 1000))


if __name__ == '__main__':
    # d = jdatetime.date(1399, 11, 30, locale=PERSIAN_LOCALE)
    # print(d)
    # print(d.togregorian())
    td = datetime.today()
    start_date = datetime.now() - timedelta(30 * 6)
    print(start_date)
