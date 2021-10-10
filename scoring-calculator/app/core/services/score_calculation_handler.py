from app.core.constants import ZERO
from app.core.data.caching.redis_caching import RedisCaching
from app.core.models.cheques import Cheque
from app.core.models.done_trades import DoneTrade
from app.core.models.loans import Loan
from app.core.models.profile import Profile
from app.core.models.undone_trades import UndoneTrade
from app.core.services.data_service import DataService
from app.core.services.score_calculation_service import ScoreCalculationService


class ScoreCalculationHandler:
    ds: DataService = None
    cs: ScoreCalculationService = None

    def __init__(self, ds: DataService = None):
        super().__init__()
        # logging.basicConfig(level=logging.INFO)
        # logger = logging.getLogger()
        self.ds = DataService() if ds is None else ds
        rds = RedisCaching(self.ds)
        self.cs = ScoreCalculationService(rds, self.ds)

    def calculate_score_by_revised_profile(self, recent_p: Profile, revised_p: Profile):
        # reset last_score_change
        revised_p.last_score_change = ZERO

        # calculate score based on profile info
        self.cs.calculate_user_profile_score(recent_p=recent_p, revised_p=revised_p)

        # update profile for last score changes
        self.ds.insert_or_update_selective_profile(p=revised_p, update_flag=True if recent_p.user_id is not None else False)
        print('ProfileScore of userId:[{}] is recalculated and new score is:[{}] \n'.format(revised_p.user_id, revised_p.score))

    def calculate_score_by_revised_loan(self, revised_ln: Loan):
        # load user profile
        revised_p = self.ds.get_user_profile(revised_ln.user_id, null_safe=True)

        # reset last_score_change
        revised_p.last_score_change = ZERO

        # load loan object from db
        recent_ln = self.ds.get_user_loan(revised_ln.user_id)

        # calculate score based on loan info
        self.cs.calculate_user_loans_score(revised_p=revised_p, recent_ln=recent_ln, revised_ln=revised_ln)

        # insert or update loan info
        self.ds.insert_or_update_selective_loan(revised_ln, update_flag=recent_ln.user_id is not None)

        # update profile for last score changes
        self.ds.insert_or_update_selective_profile(p=revised_p, update_flag=True)
        print('LoanScore of userId:[{}] is recalculated and new score is:[{}] \n'.format(revised_ln.user_id, revised_p.score))

    def calculate_score_by_revised_cheque(self, revised_ch: Cheque):
        # load user profile
        revised_p = self.ds.get_user_profile(revised_ch.user_id, null_safe=True)

        # reset last_score_change
        revised_p.last_score_change = ZERO

        # load cheque object from db
        recent_ch = self.ds.get_user_cheque(revised_ch.user_id)

        # calculate score based on cheque info
        self.cs.calculate_user_cheques_score(revised_p=revised_p, recent_ch=recent_ch, revised_ch=revised_ch)

        # insert or update cheque info
        self.ds.insert_or_update_selective_cheque(revised_ch, update_flag=recent_ch.user_id is not None)

        # update profile for last score changes
        self.ds.insert_or_update_selective_profile(p=revised_p, update_flag=True)
        print('ChequeScore of userId:[{}] is recalculated and new score is:[{}] \n'.format(revised_ch.user_id, revised_p.score))

    def calculate_score_by_revised_undone_trade(self, revised_udt: UndoneTrade):
        # load user profile
        revised_p = self.ds.get_user_profile(revised_udt.user_id, null_safe=True)

        # reset last_score_change
        revised_p.last_score_change = ZERO

        # load undone_trade object from db
        recent_udt = self.ds.get_user_undone_trade(revised_udt.user_id)

        # load done_trade object from db
        recent_dt = self.ds.get_user_done_trade(revised_udt.user_id)

        # calculate score based on undone_trade info
        self.cs.calculate_user_undone_trades_score(revised_p=revised_p, recent_udt=recent_udt, revised_udt=revised_udt, dt=recent_dt)

        # insert or update undone_trade info
        self.ds.insert_or_update_selective_undone_trade(revised_udt, update_flag=recent_udt.user_id is not None)

        # update profile for last score changes
        self.ds.insert_or_update_selective_profile(p=revised_p, update_flag=True)
        print('UndoneTradeScore of userId:[{}] is recalculated and new score is:[{}] \n'.format(revised_udt.user_id, revised_p.score))

    def calculate_score_by_revised_done_trade(self, revised_dt: DoneTrade):
        # load user profile
        revised_p = self.ds.get_user_profile(revised_dt.user_id)

        # reset last_score_change
        revised_p.last_score_change = ZERO

        # load done_trade object from db
        recent_dt = self.ds.get_user_done_trade(revised_dt.user_id)

        # calculate score based on done_trade info
        self.cs.calculate_user_done_trades_score(revised_p=revised_p, recent_dt=recent_dt, revised_dt=revised_dt)

        # insert or update done_trade info
        self.ds.insert_or_update_selective_done_trade(revised_dt, update_flag=recent_dt.user_id is not None)

        # update profile for last score changes
        self.ds.insert_or_update_selective_profile(p=revised_p, update_flag=True)
        print('DoneTradeScore of userId:[{}] is recalculated and new score is:[{}] \n'.format(revised_dt.user_id, revised_p.score))
