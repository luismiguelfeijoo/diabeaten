package com.diabeaten.edgeservice.util;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateOperations {
    public static void main(String[] args) {
        System.out.println(DateOperations.betweenHours(Time.valueOf("11:00:00"), Time.valueOf("20:22:59"), new Date()));
    }

    public static Date dateMinusHours(Date date, BigDecimal hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours.intValue() * -1 );
        return calendar.getTime();
    }

    public static boolean betweenHours(Time startHour, Time endHour, Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        String hours = calendar.get(Calendar.HOUR_OF_DAY)+ ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
        Time currentTime = Time.valueOf(hours);
        return currentTime.after(startHour) && currentTime.before(endHour);
    }

    public static long minutesBetween(Date prevDate, Date currentDate) {
       Calendar prevInstant = Calendar.getInstance();
       Calendar currentInstant = Calendar.getInstance();
       prevInstant.setTime(prevDate);
       currentInstant.setTime(currentDate);
       return Duration.between(prevInstant.toInstant(), currentInstant.toInstant()).toMinutes();
    }
}
