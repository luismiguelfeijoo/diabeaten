package com.diabeaten.informationservice.service;

import com.diabeaten.informationservice.controller.dto.InformationDTO;
import com.diabeaten.informationservice.controller.dto.RatioDTO;
import com.diabeaten.informationservice.controller.dto.SensibilityDTO;
import com.diabeaten.informationservice.exceptions.InformationNotFoundException;
import com.diabeaten.informationservice.exceptions.InvalidHourFormatException;
import com.diabeaten.informationservice.model.Information;
import com.diabeaten.informationservice.model.Ratio;
import com.diabeaten.informationservice.model.Sensibility;
import com.diabeaten.informationservice.repository.InformationRepository;
import com.diabeaten.informationservice.repository.RatioRepository;
import com.diabeaten.informationservice.repository.SensibilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class InformationService {
    @Autowired
    private InformationRepository informationRepository;
    @Autowired
    private RatioRepository ratioRepository;
    @Autowired
    private SensibilityRepository sensibilityRepository;

    @Transactional
    public Information create(InformationDTO informationDTO) {
        Information newInformation = new Information(informationDTO.getUserId(), informationDTO.getTotalBasal(), informationDTO.getDIA());
        List<Ratio> ratioList = new ArrayList<>();
        List<Sensibility> sensibilityList = new ArrayList<>();
        for (RatioDTO ratio : informationDTO.getRatios()) {
            Ratio newRatio = new Ratio(ratio.getStartHour(), ratio.getEndHour(), ratio.getRatioInGrams());
            newRatio.setInformationUser(newInformation);
            ratioList.add(newRatio);
        }
        for (SensibilityDTO sensibilityDTO : informationDTO.getSensibilities()) {
            Sensibility newSensibility = new Sensibility(sensibilityDTO.getStartHour(), sensibilityDTO.getEndHour(), sensibilityDTO.getSensibility());
            newSensibility.setInformationUser(newInformation);
            sensibilityList.add(newSensibility);
        }
        ratioRepository.saveAll(ratioList);
        sensibilityRepository.saveAll(sensibilityList);
        return informationRepository.save(newInformation);
    }

    public Information getById(Long id) {
        return informationRepository.findById(id).orElseThrow(() -> new InformationNotFoundException("There's no information with provided id"));
    }
}
