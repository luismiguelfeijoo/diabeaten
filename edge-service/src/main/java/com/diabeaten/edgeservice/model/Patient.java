package com.diabeaten.edgeservice.model;

import java.math.BigDecimal;
import java.util.List;

public class Patient {
    private Long id;
    private String username;
    private BigDecimal totalBasal;
    private List<Ratio> carbRatios;
    private List<Sensibility> sensibilities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
