package com.diabeaten.glucosebolusservice.controller.interfaces;

import com.diabeaten.glucosebolusservice.controller.dto.BolusDTO;
import com.diabeaten.glucosebolusservice.model.Bolus;
import com.diabeaten.glucosebolusservice.model.Glucose;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

public interface IBolusController {
    public List<Bolus> getBy(Long userId, Date date);
    public Bolus create(BolusDTO bolusDTO);
}
