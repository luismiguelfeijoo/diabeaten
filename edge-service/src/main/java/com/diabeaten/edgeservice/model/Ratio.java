package com.diabeaten.edgeservice.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;


public class Ratio {
    private Long id;

    private Time startHour;
    private Time endHour;
    private BigDecimal ratioInGrams;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
