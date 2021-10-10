package com.vosouq.bookkeeping.util;

import com.vosouq.bookkeeping.constant.NumberConstants;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class AppUtil {

    public static BigDecimal getZeroIfNull(BigDecimal value) {
        return isNull(value) ? BigDecimal.ZERO : value;
    }

    public static boolean isNull(Object value) {
        return value == null;
    }

    public static boolean isNotNull(Object value) {
        return !isNull(value);
    }

    public static boolean isNullOrEmpty(String value) {
        return isNull(value) || value.isEmpty();
    }

    public static String getEmptyStrIfNull(Long value) {
        return isNull(value) ? "" : value.toString();
    }

    public static boolean isListEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static <E> List<E> getEmptyList() {
        return new ArrayList<>();
    }

    public static <E> E getFirstElement(List<E> list) {
        return list.get(NumberConstants.ZERO_INT);
    }

    public static boolean datesWithoutTimeIsEqual(Date date1, Date date2) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  (dateFormat.parse(dateFormat.format(date1)).compareTo(dateFormat.parse(dateFormat.format(date2))) == NumberConstants.ZERO_INT);
    }
}
