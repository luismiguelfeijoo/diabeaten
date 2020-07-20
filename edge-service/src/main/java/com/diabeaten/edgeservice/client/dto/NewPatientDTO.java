package com.diabeaten.edgeservice.client.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

public class NewPatientDTO {
    @NotNull(message = "Username must be valid")
    @Pattern(regexp = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "You've provided an invalid email address")
    String username;
    @NotNull(message = "Password must be valid")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "You must provide a valid password")
    String password;
    private List<RatioDTO> ratios;
    private List<SensibilityDTO> sensibilities;
    @DecimalMin(value = "0")
    private BigDecimal totalBasal;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
