package com.vosouq.contract.utills;

import java.sql.Timestamp;

public class TimeUtil {

    public static Long nowInMillis() {
        return System.currentTimeMillis();
    }

    public static Timestamp nowInTimestamp() {
        return new Timestamp(nowInMillis());
    }

    public static Timestamp fromMillis(Long time) {
        return new Timestamp(time);
    }

    public static Timestamp getOneHourLater(Timestamp timestamp) {
        return Timestamp.from(timestamp.toInstant().plusSeconds(3600L));
    }

    public static Timestamp getOneDayLater(Timestamp timestamp) {
        return Timestamp.from(timestamp.toInstant().plusSeconds(86400L));
    }
}