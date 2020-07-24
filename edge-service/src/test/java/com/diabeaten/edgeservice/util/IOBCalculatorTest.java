package com.diabeaten.edgeservice.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


class IOBCalculatorTest {

    @Test
    public void calculateIOB_SameDateAsPreviousBolus() {
        BigDecimal dia = new BigDecimal("3");
        // at same moment all of the insulin is active
        assertEquals(BigDecimal.TEN, IOBCalculator.calculateIOB(dia, BigDecimal.TEN, new Date(), new Date()).setScale(0, RoundingMode.HALF_DOWN));
    }

    @Test
    public void calculateIOB_1HDelay() {
        BigDecimal dia = new BigDecimal("3");
        Date bolusDate = DateOperations.dateMinusHours(new Date(), BigDecimal.valueOf(1));
        // after 1 hour roughly 70% is still active
        assertEquals(BigDecimal.valueOf(7), IOBCalculator.calculateIOB(dia, BigDecimal.TEN, bolusDate, new Date()).setScale(0, RoundingMode.HALF_DOWN));
    }

    @Test
    public void calculateIOB_DIAPassed() {
        BigDecimal dia = new BigDecimal("3");
        Date bolusDate = DateOperations.dateMinusHours(new Date(), BigDecimal.valueOf(3));
        // after 1 hour roughly 70% is still active
        assertEquals(BigDecimal.valueOf(0), IOBCalculator.calculateIOB(dia, BigDecimal.TEN, bolusDate, new Date()).setScale(0, RoundingMode.HALF_DOWN));
    }
}