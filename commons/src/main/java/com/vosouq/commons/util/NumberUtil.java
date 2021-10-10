package com.vosouq.commons.util;

import java.util.Random;

public class NumberUtil {

    private NumberUtil() {
    }

    public static int randomUnsignedInt(int length) {

        StringBuilder builder = new StringBuilder(length);

        builder.append('1');

        while (--length > 0) {
            builder.append('0');
        }

        int start = Integer.parseInt(builder.toString());

        builder.setCharAt(0, '9');

        int end = Integer.parseInt(builder.toString());

        /**
         * for length=5 it will return
         * return 10000 + new Random().nextInt(90000);
         */
        return start + new Random().nextInt(end);
    }

}
