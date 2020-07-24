package com.diabeaten.edgeservice.service;

import com.diabeaten.edgeservice.client.GlucoseBolusClient;
import com.diabeaten.edgeservice.client.InformationClient;
import com.diabeaten.edgeservice.client.UserClient;
import com.diabeaten.edgeservice.client.dto.*;
import com.diabeaten.edgeservice.exception.AccessNotAllowedException;
import com.diabeaten.edgeservice.model.*;
import com.diabeaten.edgeservice.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PatientServiceTest {
    @Autowired
    private PatientService patientService;

    @MockBean
    private UserClient userClient;
    @MockBean
    private InformationClient informationClient;
    @MockBean
    private GlucoseBolusClient glucoseBolusClient;

    @Autowired
    JwtUtil jwtUtil;
    User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId((long) 1);
        user.setUsername("test@gmail.com");
        user.setName("test");
        user.setMonitors(new ArrayList<>());
    }

    @Test
    public void getAll() {
        when(userClient.getPatients(Mockito.anyString())).thenReturn(new ArrayList<>());
        assertEquals(0, patientService.getAll().size());
    }

    @Test
    public void getById_notAccessible() {
        when(userClient.getPatientById(Mockito.anyString(), Mockito.anyLong())).thenReturn(user);
        assertThrows(AccessNotAllowedException.class, () -> patientService.getById(user, (long) 2));
    }

    @Test
    public void getById_AccessGranted() {
        Information information = new Information();
        information.setTotalBasal(BigDecimal.ONE);
        information.setDIA(BigDecimal.ONE);
        information.setCarbRatios(new ArrayList<>());
        information.setSensibilities(new ArrayList<>());

        when(userClient.getPatientById(Mockito.anyString(), Mockito.anyLong())).thenReturn(user);
        when(informationClient.getById(Mockito.anyString(), Mockito.anyLong())).thenReturn((information));
        assertEquals(Patient.class, patientService.getById(user, (long) 1).getClass());
    }

    @Test
    public void create() {
        NewPatientDTO newPatientDTO = new NewPatientDTO();
        newPatientDTO.setTotalBasal(BigDecimal.ONE);
        newPatientDTO.setDIA(BigDecimal.ONE);
        newPatientDTO.setRatios(new ArrayList<>());
        newPatientDTO.setSensibilities(new ArrayList<>());
        newPatientDTO.setUsername("test@gmail.com");
        newPatientDTO.setName("test");

        when(userClient.createPatient(Mockito.anyString(), Mockito.any(NewUserDTO.class))).thenReturn(new User());
        when(informationClient.create(Mockito.anyString(), Mockito.any(InformationDTO.class))).thenReturn((new Information()));

        assertEquals(User.class, patientService.create(newPatientDTO).getClass());
    }

    @Test
    public void update() {

        UpdatePatientDTO updatePatientDTO = new UpdatePatientDTO();
        updatePatientDTO.setTotalBasal(BigDecimal.ONE);
        updatePatientDTO.setDia(BigDecimal.ONE);
        updatePatientDTO.setRatios(new ArrayList<>());
        updatePatientDTO.setSensibilities(new ArrayList<>());
        updatePatientDTO.setUsername("test@gmail.com");
        updatePatientDTO.setName("test");

        when(userClient.updatePatient(Mockito.anyString(), Mockito.anyLong(), Mockito.any(UpdateUserDTO.class))).thenReturn(user);
        when(informationClient.update(Mockito.anyString(), Mockito.anyLong(), Mockito.any(UpdateInformationDTO.class))).thenReturn((new Information()));

        assertEquals(Patient.class, patientService.update(user, (long) 1 ,updatePatientDTO).getClass());
    }

    @Test
    public void getGlucoseByUserId() {
        when(glucoseBolusClient.getByUserId(Mockito.anyString(), Mockito.anyLong())).thenReturn(new ArrayList<>());
        assertEquals(0, patientService.getGlucoseByUserId(user, (long) 1).size());
    }

    @Test
    public void addGlucose() {
        GlucoseDTO glucoseDTO = new GlucoseDTO();
        glucoseDTO.setDate(new Date());
        glucoseDTO.setGlucose(BigDecimal.TEN);

        when(userClient.getPatientById(Mockito.anyString(), Mockito.anyLong())).thenReturn(user);
        when(glucoseBolusClient.create(Mockito.anyString(), Mockito.any(GlucoseDTO.class))).thenReturn(new Glucose());
        assertEquals(Glucose.class, patientService.addGlucose(user, (long) 1, glucoseDTO).getClass());
    }

    @Test
    public void getBolusByUserId() {
        when(glucoseBolusClient.getBy(Mockito.anyString(), Mockito.anyLong(), Mockito.any())).thenReturn(new ArrayList<>());
        assertEquals(0, patientService.getBolusByUserId(user, (long) 1).size());
    }

    @Test
    public void addBolus() {
        BolusDTO bolusDTO = new BolusDTO();
        bolusDTO.setDate(new Date());
        bolusDTO.setGlucose(BigDecimal.TEN);
        bolusDTO.setChBolus(BigDecimal.ZERO);
        bolusDTO.setCorrectionBolus(BigDecimal.ZERO);
        when(userClient.getPatientById(Mockito.anyString(), Mockito.anyLong())).thenReturn(user);
        when(glucoseBolusClient.create(Mockito.anyString(), Mockito.any(BolusDTO.class))).thenReturn(new Bolus());
        assertEquals(Bolus.class, patientService.addBolus(user, (long) 1, bolusDTO).getClass());
    }

    @Test
    public void getAllUsers() {
        when(userClient.getAll(Mockito.anyString())).thenReturn(new ArrayList<>());
        assertEquals(0, patientService.getAllUsers().size());
    }

    @Test
    public void delete() {
        patientService.delete((long) 1);
    }

    @Test
    public void calculateBolus_withoutPreviousBolus() {
        Ratio ratio = new Ratio();
        ratio.setStartHour(Time.valueOf("00:00:00"));
        ratio.setEndHour(Time.valueOf("23:59:59"));
        ratio.setRatioInGrams(BigDecimal.TEN);

        Sensibility sensibility = new Sensibility();
        sensibility.setStartHour(Time.valueOf("00:00:00"));
        sensibility.setEndHour(Time.valueOf("23:59:59"));
        sensibility.setSensibility(BigDecimal.TEN);

        Information information = new Information();
        information.setDIA(BigDecimal.TEN);
        information.setTotalBasal(BigDecimal.TEN);
        information.setCarbRatios(Stream.of(ratio).collect(Collectors.toList()));
        information.setSensibilities(Stream.of(sensibility).collect(Collectors.toList()));

        when(informationClient.getById(Mockito.anyString(),Mockito.anyLong())).thenReturn(information);

        BolusParamsDTO bolusParamsDTO = new BolusParamsDTO();
        bolusParamsDTO.setDate(new Date());
        bolusParamsDTO.setCarbs(BigDecimal.TEN);
        bolusParamsDTO.setGlucose(BigDecimal.TEN);

        assertEquals(Bolus.class, patientService.calculateBolus(user, (long) 1, bolusParamsDTO).getClass());

    }

    @Test
    public void calculateBolus_withPreviousBolus() {
        Ratio ratio = new Ratio();
        ratio.setStartHour(Time.valueOf("00:00:00"));
        ratio.setEndHour(Time.valueOf("23:59:59"));
        ratio.setRatioInGrams(BigDecimal.TEN);

        Sensibility sensibility = new Sensibility();
        sensibility.setStartHour(Time.valueOf("00:00:00"));
        sensibility.setEndHour(Time.valueOf("23:59:59"));
        sensibility.setSensibility(BigDecimal.TEN);

        Information information = new Information();
        information.setDIA(BigDecimal.ZERO);
        information.setTotalBasal(BigDecimal.TEN);
        information.setCarbRatios(Stream.of(ratio).collect(Collectors.toList()));
        information.setSensibilities(Stream.of(sensibility).collect(Collectors.toList()));

        when(informationClient.getById(Mockito.anyString(),Mockito.anyLong())).thenReturn(information);

        Bolus bolus = new Bolus();
        bolus.setChBolus(BigDecimal.TEN);
        bolus.setCorrectionBolus(BigDecimal.ZERO);
        bolus.setDate(new Date());

        when(glucoseBolusClient.getBy(Mockito.anyString(), Mockito.anyLong(), Mockito.any())).thenReturn(Stream.of(bolus).collect(Collectors.toList()));

        BolusParamsDTO bolusParamsDTO = new BolusParamsDTO();
        bolusParamsDTO.setDate(new Date());
        bolusParamsDTO.setCarbs(BigDecimal.TEN);
        bolusParamsDTO.setGlucose(BigDecimal.TEN);

        assertEquals(Bolus.class, patientService.calculateBolus(user, (long) 1, bolusParamsDTO).getClass());

    }
}