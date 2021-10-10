import json
from datetime import datetime

from pydantic import BaseModel


class UndoneTrade(BaseModel):
    user_id: int = None
    calculation_start_date: datetime = None

    # count fields
    undue_trades_count: int = None
    undue_trades_count_score: float = None

    past_due_trades_count: int = None
    past_due_trades_count_score: float = None

    arrear_trades_count: int = None
    arrear_trades_count_score: float = None

    # balance fields
    undue_trades_total_balance_of_last_year: float = None
    undue_trades_total_balance_of_last_year_score: float = None

    past_due_trades_total_balance_of_last_year: float = None
    past_due_trades_total_balance_of_last_year_score: float = None

    arrear_trades_total_balance_of_last_year: float = None
    arrear_trades_total_balance_of_last_year_score: float = None
