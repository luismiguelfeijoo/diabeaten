package com.diabeaten.edgeservice.client.dto;

import java.math.BigDecimal;
import java.util.List;

public class UpdatePatientDTO {
    private String username;
    private String name;
    private BigDecimal totalBasal;
    private BigDecimal dia;
    private List<RatioDTO> ratios;
    private List<SensibilityDTO> sensibilities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotalBasal() {
        return totalBasal;
    }

    public void setTotalBasal(BigDecimal totalBasal) {
        this.totalBasal = totalBasal;
    }

    public BigDecimal getDia() {
        return dia;
    }

    public void setDia(BigDecimal dia) {
        this.dia = dia;
    }

    public List<RatioDTO> getRatios() {
        return ratios;
    }

    public void setRatios(List<RatioDTO> ratios) {
        this.ratios = ratios;
    }

    public List<SensibilityDTO> getSensibilities() {
        return sensibilities;
    }

    public void setSensibilities(List<SensibilityDTO> sensibilities) {
        this.sensibilities = sensibilities;
    }
}
