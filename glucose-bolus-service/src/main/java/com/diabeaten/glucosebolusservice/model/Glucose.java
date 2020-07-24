package com.diabeaten.glucosebolusservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Glucose {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Date date;
    private BigDecimal glucose;

    public Glucose() {
    }

    public Glucose(Long userId, Date date, BigDecimal glucose) {
        setUserId(userId);
        setDate(date);
        setGlucose(glucose);
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
}
