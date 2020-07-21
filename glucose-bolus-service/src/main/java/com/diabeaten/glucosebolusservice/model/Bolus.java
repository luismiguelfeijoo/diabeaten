package com.diabeaten.glucosebolusservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Bolus {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Date date;
    private BigDecimal glucose;
    private BigDecimal correctionBolus;
    private BigDecimal chBolus;

    public Bolus() {
    }

    public Bolus(Long userId, Date date, BigDecimal glucose, BigDecimal correctionBolus, BigDecimal chBolus) {
        setUserId(userId);
        setDate(date);
        setGlucose(glucose);
        setCorrectionBolus(correctionBolus);
        setChBolus(chBolus);
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
