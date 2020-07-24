package com.diabeaten.informationservice.model;

import com.diabeaten.informationservice.exceptions.InvalidHourFormatException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;

@Entity
public class Sensibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Time startHour;
    private Time endHour;
    private BigDecimal sensibility;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Information informationUser;

    public Sensibility() {
    }

    public Sensibility(Time startHour, Time endHour, BigDecimal sensibility) throws InvalidHourFormatException {
        setInterval(startHour, endHour);
        setSensibility(sensibility);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Time getStartHour() {
        return startHour;
    }

    public void setStartHour(Time startHour) {
        this.startHour = startHour;
    }

    public Time getEndHour() {
        return endHour;
    }

    public void setEndHour(Time endHour) {
        this.endHour = endHour;
    }

    public BigDecimal getSensibility() {
        return sensibility;
    }

    public void setSensibility(BigDecimal sensibility) {
        this.sensibility = sensibility;
    }

    public Information getInformationUser() {
        return informationUser;
    }

    public void setInformationUser(Information informationUser) {
        this.informationUser = informationUser;
    }

    public void setInterval(Time startHour, Time endHour) throws InvalidHourFormatException {
        if (startHour.after(endHour)) throw new InvalidHourFormatException("You must provide a end time after starting time");
        setStartHour(startHour);
        setEndHour(endHour);
    }
}
