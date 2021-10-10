# from dependency_injector.wiring import inject
from typing import List

from fastapi import APIRouter, Query

from app.core.data.caching.redis_caching import RedisCaching
from app.core.models.dtos.cheque_status_dto import ChequesStatusDTO
from app.core.models.dtos.loan_status_dto import LoansStatusDTO
from app.core.models.dtos.score_boundaries_dto import ScoreBoundariesDTO
from app.core.models.dtos.score_change_dto import ScoreChangeDTO
from app.core.models.dtos.score_details_dto import ScoreDetailsDTO
from app.core.models.dtos.score_distribution_dto import ScoreDistributionDTO
from app.core.models.dtos.score_status_dto import ScoreStatusDTO
from app.core.models.dtos.score_time_series_dto import ScoreTimeSeriesDTO
from app.core.models.dtos.user_score_dto import UserScoreDTO
from app.core.models.dtos.vosouq_status_dto import VosouqStatusDTO
from app.core.models.score_gauges import ScoreGauge
from app.core.services.data_service import DataService
from app.core.settings import redis_reset

router = APIRouter()
ds = DataService()

# reset redis cash server
if bool(redis_reset):
    RedisCaching(ds).cache_rules()


# @router.get("/score-gauges", response_model=List[ScoreGauge])
@router.get("/score-gauges", response_model=List[ScoreGauge], responses={200: {"model": ScoreGauge}})
async def get_score_gauges():
    return ds.get_score_gauges()


# noinspection PyPep8Naming
@router.put("/create-profile/{userId}")
async def create_profile(userId: int):
    ds.create_profile(userId)


@router.get("/score-boundaries", response_model=ScoreBoundariesDTO)
async def get_score_boundaries():
    return ds.get_score_boundaries()


# noinspection PyPep8Naming
@router.get("/score-status/{userId}", response_model=ScoreStatusDTO)
async def get_score_status(userId: int):
    return ds.get_score_status(user_id=userId)


# noinspection PyPep8Naming
@router.get("/vosouq-status/{userId}", response_model=VosouqStatusDTO)
async def get_vosouq_status(userId: int):
    return ds.get_vosouq_status(user_id=userId)


# noinspection PyPep8Naming
@router.get("/loans-status/{userId}", response_model=LoansStatusDTO)
async def get_loans_status(userId: int):
    return ds.get_loans_status(user_id=userId)


# noinspection PyPep8Naming
@router.get("/cheques-status/{userId}", response_model=ChequesStatusDTO)
async def get_cheques_status(userId: int):
    return ds.get_cheques_status(user_id=userId)


# noinspection PyPep8Naming
@router.get("/score-time-series/{userId}/filter/{number_of_days}", response_model=List[ScoreTimeSeriesDTO])
async def get_score_time_series(userId: int, number_of_days: int):
    return ds.get_score_time_series(userId, number_of_days)


# noinspection PyPep8Naming
@router.get("/score-details/{userId}", response_model=ScoreDetailsDTO)
async def get_score_details(userId: int):
    return ds.get_score_details(user_id=userId)


# noinspection PyPep8Naming
@router.get("/score-distributions", response_model=List[ScoreDistributionDTO])
async def get_score_distributions():
    return ds.get_score_distributions()


# noinspection PyPep8Naming
@router.get("/score-changes/{userId}", response_model=List[ScoreChangeDTO])
async def get_score_changes(userId: int):
    return ds.get_score_changes(userId)


# noinspection PyPep8Naming
@router.get("/scores", response_model=List[UserScoreDTO])
async def get_users_scores(userIds: List[int] = Query(None), pageSize: int = Query(None), page: int = Query(None), scoreDescSort: bool = Query(None)):
    return ds.get_users_scores(userIds, pageSize, page, scoreDescSort)
