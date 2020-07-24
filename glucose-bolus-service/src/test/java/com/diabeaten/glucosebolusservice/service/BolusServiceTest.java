package com.diabeaten.glucosebolusservice.service;

import com.diabeaten.glucosebolusservice.controller.dto.BolusDTO;
import com.diabeaten.glucosebolusservice.model.Bolus;
import com.diabeaten.glucosebolusservice.repository.BolusRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BolusServiceTest {
    @Autowired
    private BolusService bolusService;
    @MockBean
    private BolusRepository bolusRepository;

    @Test
    public void getByUserId() {
        when(bolusRepository.findByUserId(Mockito.anyLong())).thenReturn(new ArrayList<>());
        assertEquals(0, bolusService.getByUserId((long) 1).size());
    }

    @Test
    public void getByUserIdAndPeriod() {
        when(bolusRepository.findByUserIdAndDateAfter(Mockito.anyLong(), Mockito.any(Date.class))).thenReturn(new ArrayList<>());
        assertEquals(0, bolusService.getByUserAndPeriod((long) 1, new Date()).size());
    }

    @Test
    public void create() {
        BolusDTO bolusDTO = new BolusDTO();
        bolusDTO.setChBolus(BigDecimal.ZERO);
        bolusDTO.setCorrectionBolus(BigDecimal.ZERO);
        bolusDTO.setDate(new Date());
        bolusDTO.setGlucose(BigDecimal.TEN);
        bolusDTO.setUserId((long) 1);
        when(bolusRepository.save(Mockito.any(Bolus.class))).thenReturn(new Bolus());
        Bolus createdBolus = bolusService.create(bolusDTO);
        assertEquals(Bolus.class, createdBolus.getClass());
    }

}