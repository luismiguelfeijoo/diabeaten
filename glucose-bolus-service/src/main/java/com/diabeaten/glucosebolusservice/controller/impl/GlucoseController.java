package com.diabeaten.glucosebolusservice.controller.impl;

import com.diabeaten.glucosebolusservice.controller.dto.GlucoseDTO;
import com.diabeaten.glucosebolusservice.controller.interfaces.IGlucoseController;
import com.diabeaten.glucosebolusservice.model.Glucose;
import com.diabeaten.glucosebolusservice.service.GlucoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class GlucoseController implements IGlucoseController {
    @Autowired
    private GlucoseService glucoseService;

    @GetMapping("/glucose/{userId}")
    @Override
    public List<Glucose> getByUserId(@PathVariable(name = "userId") Long userId) {
        return glucoseService.getByUserId(userId);
    }

    @PostMapping("/glucose")
    @Override
    public Glucose create(@RequestBody @Valid GlucoseDTO glucoseDTO) {
        return glucoseService.create(glucoseDTO);
    }
}
