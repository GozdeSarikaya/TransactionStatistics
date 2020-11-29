package com.n26.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;

public class AppUtility {

    public static long CalculateTimeDiffs(Instant timestamp) {
        return Instant.now().atZone(ZoneId.systemDefault()).toEpochSecond() - timestamp.toEpochMilli() / 1000;
    }

    public static BigDecimal ToBigDecimal(double input) {
        return BigDecimal.valueOf(input).setScale(2, RoundingMode.HALF_UP);
    }
}
