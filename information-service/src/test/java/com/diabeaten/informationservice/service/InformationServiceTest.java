package com.diabeaten.informationservice.service;

import com.diabeaten.informationservice.controller.dto.InformationDTO;
import com.diabeaten.informationservice.controller.dto.RatioDTO;
import com.diabeaten.informationservice.controller.dto.SensibilityDTO;
import com.diabeaten.informationservice.controller.dto.UpdateInformationDTO;
import com.diabeaten.informationservice.exceptions.InformationNotFoundException;
import com.diabeaten.informationservice.model.Information;
import com.diabeaten.informationservice.model.Ratio;
import com.diabeaten.informationservice.repository.InformationRepository;
import com.diabeaten.informationservice.repository.RatioRepository;
import com.diabeaten.informationservice.repository.SensibilityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class InformationServiceTest {
    @Autowired
    private InformationService informationService;
    @MockBean
    private InformationRepository informationRepository;
    @MockBean
    private RatioRepository ratioRepository;
    @MockBean
    private SensibilityRepository sensibilityRepository;


    @Test
    public void getById_NoUser_Exception() {
        assertThrows(InformationNotFoundException.class, () -> { informationService.getById((long) 2);});
    }

    @Test
    public void getById() {
        Information information = new Information((long) 1, new BigDecimal("20"), new BigDecimal("3"));
        when(informationRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(information));
        Information foundPatient = informationService.getById((long)1);
        assertEquals(information.getDIA(), foundPatient.getDIA());
        assertEquals(information.getTotalBasal(), foundPatient.getTotalBasal());
        assertEquals(information.getUserId(), foundPatient.getUserId());
    }

    @Test
    public void create() {
        //Monitor monitor = new Monitor("test@test.com", "test", "test");
        RatioDTO ratio = new RatioDTO();
        ratio.setEndHour(Time.valueOf("23:00:00"));
        ratio.setStartHour(Time.valueOf("00:00:00"));
        ratio.setRatioInGrams(new BigDecimal("5"));

        SensibilityDTO sensibility = new SensibilityDTO();
        sensibility.setEndHour(Time.valueOf("23:00:00"));
        sensibility.setStartHour(Time.valueOf("00:00:00"));
        sensibility.setSensibility(new BigDecimal("50"));

        InformationDTO newInformation = new InformationDTO();
        newInformation.setDIA(new BigDecimal("3"));
        newInformation.setTotalBasal(new BigDecimal("10"));
        newInformation.setRatios(Stream.of(ratio).collect(Collectors.toList()));
        newInformation.setSensibilities(Stream.of(sensibility).collect(Collectors.toList()));
        when(informationRepository.save(Mockito.any(Information.class))).thenReturn(new Information());
        Information createdInformation = informationService.create(newInformation);
        assertEquals(Information.class, createdInformation.getClass());
    }

    @Test
    public void update() {
        //Monitor monitor = new Monitor("test@test.com", "test", "test");
        RatioDTO ratio = new RatioDTO();
        ratio.setEndHour(Time.valueOf("23:00:00"));
        ratio.setStartHour(Time.valueOf("00:00:00"));
        ratio.setRatioInGrams(new BigDecimal("5"));

        SensibilityDTO sensibility = new SensibilityDTO();
        sensibility.setEndHour(Time.valueOf("23:00:00"));
        sensibility.setStartHour(Time.valueOf("00:00:00"));
        sensibility.setSensibility(new BigDecimal("50"));

        UpdateInformationDTO newInformation = new UpdateInformationDTO();
        newInformation.setDia(new BigDecimal("3"));
        newInformation.setTotalBasal(new BigDecimal("10"));
        newInformation.setRatios(Stream.of(ratio).collect(Collectors.toList()));
        newInformation.setSensibilities(Stream.of(sensibility).collect(Collectors.toList()));
        when(informationRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Information()));
        when(informationRepository.save(Mockito.any(Information.class))).thenReturn(new Information());
        Information createdInformation = informationService.update((long) 1, newInformation);
        assertEquals(Information.class, createdInformation.getClass());
    }
}