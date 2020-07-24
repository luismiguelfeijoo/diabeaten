package com.diabeaten.edgeservice.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;

public class RatioDTO {
    private Time startHour;
    private Time endHour;
    @DecimalMin(value = "0")
    private BigDecimal ratioInGrams;

    public Time getStartHour() {
        return startHour;
    }

    public void setStartHour(Time startHour) {
        this.startHour = startHour;
    }

    public Time getEndHour() {
        return endHour;
    }

    public void setEndHour(Time endHour) {
        this.endHour = endHour;
    }

    public BigDecimal getRatioInGrams() {
        return ratioInGrams;
    }

    public void setRatioInGrams(BigDecimal ratioInGrams) {
        this.ratioInGrams = ratioInGrams;
    }
}
