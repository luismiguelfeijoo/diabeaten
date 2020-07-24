package com.diabeaten.informationservice.model;

import com.diabeaten.informationservice.exceptions.InvalidHourFormatException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;

@Entity
public class Ratio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Time startHour;
    private Time endHour;
    private BigDecimal ratioInGrams;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Information informationUser;


    public Ratio() {
    }

    public Ratio(Time startHour, Time endHour, BigDecimal ratioInGrams) throws InvalidHourFormatException{
        setInterval(startHour, endHour);
        setRatioInGrams(ratioInGrams);
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

    public BigDecimal getRatioInGrams() {
        return ratioInGrams;
    }

    public void setRatioInGrams(BigDecimal ratioInGrams) {
        this.ratioInGrams = ratioInGrams;
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
