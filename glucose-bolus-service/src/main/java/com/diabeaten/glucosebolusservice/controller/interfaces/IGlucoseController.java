package com.diabeaten.glucosebolusservice.controller.interfaces;

import com.diabeaten.glucosebolusservice.controller.dto.GlucoseDTO;
import com.diabeaten.glucosebolusservice.model.Glucose;

import java.util.List;

public interface IGlucoseController {
    public List<Glucose> getByUserId(Long userId);
    public Glucose create(GlucoseDTO glucoseDTO);
}
