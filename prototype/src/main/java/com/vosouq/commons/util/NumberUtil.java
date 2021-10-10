package com.vosouq.commons.util;

import java.util.Random;

public class NumberUtil {

    public static int generateRandomInteger(int length) {
        return Integer.parseInt(generateRandomNumber(length));
    }

    public static long generateRandomLong(int length) {
        return Long.parseLong(generateRandomNumber(length));
    }

    private static String generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder(length);

        int firstNumber = random.nextInt(10);
        while (firstNumber == 0) {
            firstNumber = random.nextInt(10);
        }

        --length;
        builder.append(firstNumber);
        while (length > 0) {
            builder.append(random.nextInt(10));
            --length;
        }

        return builder.toString();
    }

}
