package com.vosouq.contract.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum BodyStatus {

    NOT_COLORED,
    SMOOTHED_NOT_COLORED,
    ONE_SPOT_COLORED,
    TWO_SPOT_COLORED,
    MULTI_SPOT_COLORED,
    MUDGUARD_COLORED,
    MUDGUARD_CHANGED,
    ONE_DOOR_COLORED,
    TWO_DOOR_COLORED,
    DOOR_CHANGED,
    HOOD_COLOR,
    HOOD_CHANGED,
    ROUND_COLORED,
    COMPLETE_COLORED,
    CRASHED,
    ROOM_CHANGED,
    BURNED,
    SCRAPED

}
