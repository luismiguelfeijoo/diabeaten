package com.diabeaten.edgeservice.client.dto;

import java.math.BigDecimal;
import java.util.Date;

public class BolusParamsDTO {
    private BigDecimal glucose;
    private Date date;
    private BigDecimal carbs;

    public BigDecimal getGlucose() {
        return glucose;
    }

    public void setGlucose(BigDecimal glucose) {
        this.glucose = glucose;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getCarbs() {
        return carbs;
    }

    public void setCarbs(BigDecimal carbs) {
        this.carbs = carbs;
    }
}
