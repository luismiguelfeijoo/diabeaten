package com.diabeaten.glucosebolusservice.service;

import com.diabeaten.glucosebolusservice.controller.dto.BolusDTO;
import com.diabeaten.glucosebolusservice.controller.dto.GlucoseDTO;
import com.diabeaten.glucosebolusservice.model.Bolus;
import com.diabeaten.glucosebolusservice.model.Glucose;
import com.diabeaten.glucosebolusservice.repository.BolusRepository;
import com.diabeaten.glucosebolusservice.repository.GlucoseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class GlucoseServiceTest {
    @Autowired
    private GlucoseService glucoseService;
    @MockBean
    private GlucoseRepository glucoseRepository;

    @Test
    public void getByUserId() {
        when(glucoseRepository.findByUserId(Mockito.anyLong())).thenReturn(new ArrayList<>());
        assertEquals(0, glucoseService.getByUserId((long) 1).size());
    }

    @Test
    public void create() {
        GlucoseDTO glucoseDTO = new GlucoseDTO();
        glucoseDTO.setDate(new Date());
        glucoseDTO.setGlucose(BigDecimal.TEN);
        glucoseDTO.setUserId((long) 1);
        when(glucoseRepository.save(Mockito.any(Glucose.class))).thenReturn(new Glucose());
        Glucose createdBolus = glucoseService.create(glucoseDTO);
        assertEquals(Glucose.class, createdBolus.getClass());
    }

}