package com.diabeaten.edgeservice.client.dto;

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
}
