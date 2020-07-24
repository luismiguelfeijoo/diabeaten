package com.diabeaten.edgeservice.model;

import java.math.BigDecimal;
import java.util.List;

public class Information {

    private Long userId;
    private BigDecimal totalBasal;
    private BigDecimal DIA;
    private List<Ratio> carbRatios;
    private List<Sensibility> sensibilities;

    public Information() {
    }

    public Information(Long userId, BigDecimal totalBasal, BigDecimal DIA) {
        setUserId(userId);
        setTotalBasal(totalBasal);
        setDIA(DIA);
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

    public BigDecimal getDIA() {
        return DIA;
    }

    public void setDIA(BigDecimal DIA) {
        this.DIA = DIA;
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
