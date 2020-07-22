package com.diabeaten.edgeservice.model;

import java.math.BigDecimal;
import java.util.List;

public class Patient {
    private Long id;
    private String username;
    private String name;
    private BigDecimal totalBasal;
    private BigDecimal DIA;
    private List<Ratio> ratios;
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

    public BigDecimal getDIA() {
        return DIA;
    }

    public void setDIA(BigDecimal DIA) {
        this.DIA = DIA;
    }

    public List<Ratio> getRatios() {
        return ratios;
    }

    public void setRatios(List<Ratio> ratios) {
        this.ratios = ratios;
    }

    public List<Sensibility> getSensibilities() {
        return sensibilities;
    }

    public void setSensibilities(List<Sensibility> sensibilities) {
        this.sensibilities = sensibilities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
