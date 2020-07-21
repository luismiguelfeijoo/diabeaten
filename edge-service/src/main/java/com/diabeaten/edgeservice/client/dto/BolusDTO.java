package com.diabeaten.edgeservice.client.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class BolusDTO {
    private Long userId;
    private Date date;
    @DecimalMin(value = "0")
    private BigDecimal glucose;
    @DecimalMin(value = "0")
    private BigDecimal correctionBolus;
    @DecimalMin(value = "0")
    private BigDecimal chBolus;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getGlucose() {
        return glucose;
    }

    public void setGlucose(BigDecimal glucose) {
        this.glucose = glucose;
    }

    public BigDecimal getCorrectionBolus() {
        return correctionBolus;
    }

    public void setCorrectionBolus(BigDecimal correctionBolus) {
        this.correctionBolus = correctionBolus;
    }

    public BigDecimal getChBolus() {
        return chBolus;
    }

    public void setChBolus(BigDecimal chBolus) {
        this.chBolus = chBolus;
    }
}
