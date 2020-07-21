package com.diabeaten.informationservice.controller.dto;

import com.diabeaten.informationservice.model.Ratio;
import com.diabeaten.informationservice.model.Sensibility;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class InformationDTO {
    @NotNull
    private Long userId;
    private List<RatioDTO> ratios;
    private List<SensibilityDTO> sensibilities;
    @DecimalMin(value = "0")
    private BigDecimal totalBasal;
    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    private BigDecimal DIA;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
