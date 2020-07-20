package com.diabeaten.edgeservice.model;

import java.math.BigDecimal;
import java.util.List;

public class Information {

    private Long userId;
    private BigDecimal totalBasal;
    private List<Ratio> carbRatios;
    private List<Sensibility> sensibilities;

    public Information() {
    }

    public Information(Long userId, BigDecimal totalBasal) {
        this.userId = userId;
        this.totalBasal = totalBasal;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalBasal() {
        return totalBasal;
    }

    public void setTotalBasal(BigDecimal totalBasal) {
        this.totalBasal = totalBasal;
    }

    public List<Ratio> getCarbRatios() {
        return carbRatios;
    }

    public void setCarbRatios(List<Ratio> carbRatios) {
        this.carbRatios = carbRatios;
    }

    public List<Sensibility> getSensibilities() {
        return sensibilities;
    }

    public void setSensibilities(List<Sensibility> sensibilities) {
        this.sensibilities = sensibilities;
    }
}
