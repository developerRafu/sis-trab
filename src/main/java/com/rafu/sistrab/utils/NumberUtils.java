package com.rafu.sistrab.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {
    private NumberUtils() {
    }

    public static boolean isGreater(final BigDecimal first, final BigDecimal second) {
        return first.compareTo(second) > 0;
    }

    public static boolean isEqual(final BigDecimal first, final BigDecimal second) {
        return first.compareTo(second) == 0;
    }

    public static String convertToBRL(BigDecimal amount) {
        Locale brLocale = new Locale("pt", "BR");
        NumberFormat brFormat = NumberFormat.getCurrencyInstance(brLocale);
        return brFormat.format(amount);
    }
}
