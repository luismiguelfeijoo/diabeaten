package com.diabeaten.glucosebolusservice.service;

import com.diabeaten.glucosebolusservice.controller.dto.GlucoseDTO;
import com.diabeaten.glucosebolusservice.model.Glucose;
import com.diabeaten.glucosebolusservice.repository.GlucoseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GlucoseService {
    @Autowired
    private GlucoseRepository glucoseRepository;

    public List<Glucose> getByUserId(Long userId) {
        return glucoseRepository.findByUserId(userId);
    }

    public Glucose create(GlucoseDTO glucoseDTO) {
        Glucose newGlucose = new Glucose(glucoseDTO.getUserId(), glucoseDTO.getDate(), glucoseDTO.getGlucose());
        return glucoseRepository.save(newGlucose);
    }
}
