package com.diabeaten.glucosebolusservice.service;

import com.diabeaten.glucosebolusservice.controller.dto.BolusDTO;
import com.diabeaten.glucosebolusservice.model.Bolus;
import com.diabeaten.glucosebolusservice.repository.BolusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BolusService {
    @Autowired
    private BolusRepository bolusRepository;
    public List<Bolus> getByUserId(Long userId) {
        return bolusRepository.findByUserId(userId);
    }

    public List<Bolus> getByUserAndPeriod(Long userId, Date date) {
        return bolusRepository.findByUserIdAndDateAfter(userId, date);
    }

    public Bolus create(BolusDTO bolusDTO) {
        Bolus newBolus = new Bolus(bolusDTO.getUserId(), bolusDTO.getDate(), bolusDTO.getGlucose(), bolusDTO.getCorrectionBolus(), bolusDTO.getChBolus());
        return bolusRepository.save(newBolus);
    }
}
