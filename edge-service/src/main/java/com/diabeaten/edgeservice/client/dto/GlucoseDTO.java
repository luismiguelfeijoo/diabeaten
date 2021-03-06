package com.diabeaten.edgeservice.client.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class GlucoseDTO {
    private Long userId;
    @NotNull
    private Date date;
    @DecimalMin("40")
    private BigDecimal glucose;

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
