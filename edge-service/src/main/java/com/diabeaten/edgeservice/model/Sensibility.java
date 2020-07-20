package com.diabeaten.edgeservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Time;

public class Sensibility {

    private Long id;
    private Time startHour;
    private Time endHour;
    private BigDecimal sensibility;



    public Sensibility() {
    }



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

    public BigDecimal getSensibility() {
        return sensibility;
    }

    public void setSensibility(BigDecimal sensibility) {
        this.sensibility = sensibility;
    }


}
