from datetime import date

from pydantic import BaseModel

from app.core.models.scoring_enums import ProfileMilitaryServiceStatusEnum


class Profile(BaseModel):
    user_id: int = None

    has_kyc: bool = None
    has_kyc_score: float = None

    military_service_status: ProfileMilitaryServiceStatusEnum = None
    military_service_status_score: float = None

    sim_card_ownership: bool = None
    sim_card_ownership_score: float = None

    address_verification: bool = None
    address_verification_score: float = None

    membership_date: date = None
    membership_date_score: float = None

    recommended_to_others_count: int = None
    recommended_to_others_count_score: float = None

    number_of_times_star_received: int = None
    number_of_times_star_received_score: float = None

    star_count_average: int = None
    star_count_average_score: float = None

    score: float = 0
    identities_score: float = None
    histories_score: float = None
    volumes_score: float = None
    timeliness_score: float = None
    last_score_change: float = None
    last_update_date: date = None

    # class Config:
    #     orm_mode = True

    # Profile = create_model(
    #     'BarModel',
    #     apple='russet',
    #     banana='yellow',
    #     __base__=FooModel,
    # )
