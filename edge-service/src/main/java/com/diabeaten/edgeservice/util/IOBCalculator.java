package com.diabeaten.edgeservice.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class IOBCalculator {
    private BigDecimal e = new BigDecimal(Math.E);

    public static BigDecimal calculateIOB(BigDecimal dia, BigDecimal insulinUsed, Date prevBolusDate, Date currentBolusDate) {
        BigDecimal peak = new BigDecimal("75");
        System.out.println("peak: " +peak);
        BigDecimal end = dia.multiply(new BigDecimal("60"));
        System.out.println("end: " +end);
        long minsAgo = DateOperations.minutesBetween(prevBolusDate, currentBolusDate);
        System.out.println(minsAgo);
        BigDecimal tau = peak.multiply((BigDecimal.ONE.subtract(peak.divide(end, 5, RoundingMode.HALF_EVEN))).divide(BigDecimal.ONE.subtract(BigDecimal.valueOf(2).multiply(peak.divide(end, 5, RoundingMode.HALF_EVEN))), 5, RoundingMode.HALF_EVEN));
        System.out.println("tau: " +tau);
        BigDecimal a = new BigDecimal("2").multiply(tau).divide(end, 2, RoundingMode.HALF_EVEN); // rise time factor
        System.out.println("a: " +a);
        BigDecimal S = BigDecimal.ONE.divide(BigDecimal.ONE.subtract(a).add((BigDecimal.ONE.add(a)).multiply( BigDecimal.valueOf(Math.exp( (end.negate().divide(tau, 5, RoundingMode.HALF_EVEN)).doubleValue())))), 5, RoundingMode.HALF_EVEN);
        System.out.println("s: " +S);
        //BigDecimal result = insulinUsed.multiply(BigDecimal.ONE.subtract(S.multiply(BigDecimal.ONE.subtract(a)).multiply((BigDecimal.valueOf(minsAgo).pow(2)).divide(tau.multiply(end).multiply(BigDecimal.ONE.subtract(a)), 2, RoundingMode.HALF_EVEN).subtract(BigDecimal.valueOf(minsAgo).divide(tau, 2, RoundingMode.HALF_EVEN).subtract(BigDecimal.ONE))).multiply(BigDecimal.valueOf(Math.exp(BigDecimal.valueOf(minsAgo).negate().divide(tau, 2, RoundingMode.HALF_EVEN).doubleValue())).add(BigDecimal.ONE))));
        System.out.println("insulin: " +insulinUsed);
        BigDecimal iob = BigDecimal.ONE.subtract(S.multiply(BigDecimal.ONE.subtract(a)).multiply(  (( BigDecimal.valueOf(Math.pow(minsAgo, 2)).divide(tau.multiply(end).multiply(BigDecimal.ONE.subtract(a)), 5, RoundingMode.HALF_EVEN).subtract(BigDecimal.valueOf(minsAgo).divide(tau, 5, RoundingMode.HALF_EVEN)).subtract(BigDecimal.ONE)   ).multiply(  BigDecimal.valueOf(Math.exp(BigDecimal.valueOf(minsAgo).negate().divide(tau, 5, RoundingMode.HALF_EVEN).doubleValue())    ))).add(BigDecimal.ONE) ));
        System.out.println("iob: " +iob);
        BigDecimal result = insulinUsed.multiply(iob);
        System.out.println("result: " +result);
        return result;
        //BigDecimal S = BigDecimal.ONE.divide(BigDecimal.ONE.subtract(a).add(BigDecimal.ONE.add(a).multiply(BigDecimal.valueOf(Math.exp(end.negate().divide(tau, 5 , RoundingMode.HALF_EVEN).doubleValue())))),5, RoundingMode.HALF_EVEN);
        //BigDecimal result = insulinUsed.multiply(BigDecimal.ONE.subtract(S.multiply(BigDecimal.ONE.subtract(a)).multiply((BigDecimal.valueOf(minsAgo).pow(2)).divide(tau.multiply(end).multiply(BigDecimal.ONE.subtract(a)), 2, RoundingMode.HALF_EVEN).subtract(BigDecimal.valueOf(minsAgo).divide(tau, 2, RoundingMode.HALF_EVEN).subtract(BigDecimal.ONE))).multiply(BigDecimal.valueOf(Math.exp(BigDecimal.valueOf(minsAgo).negate().divide(tau, 2, RoundingMode.HALF_EVEN).doubleValue())).add(BigDecimal.ONE))));
        // S.multiply(BigDecimal.ONE.subtract(a)).multiply( (BigDecimal.valueOf(minsAgo).divide(tau.multiply(end).multiply(BigDecimal.ONE.subtract(a)), 5, RoundingMode.HALF_EVEN).subtract(BigDecimal.valueOf(minsAgo).divide(tau, 5, RoundingMode.HALF_EVEN)).subtract(BigDecimal.ONE)).multiply(BigDecimal.valueOf(Math.exp(BigDecimal.valueOf(minsAgo).negate().divide(tau, 5, RoundingMode.HALF_EVEN).doubleValue()))  ).add(BigDecimal.ONE) ).negate().add(BigDecimal.ONE)
        //BigDecimal tau = (peak.multiply(BigDecimal.ONE.subtract(peak.divide(end, 9, RoundingMode.HALF_DOWN)).divide(BigDecimal.ONE.subtract((BigDecimal.valueOf(2).multiply(peak).divide(end,2, RoundingMode.HALF_EVEN))), 2, RoundingMode.HALF_EVEN))); // time constant of exponential decay
    }
}
