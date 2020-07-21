package com.diabeaten.glucosebolusservice.controller.impl;

import com.diabeaten.glucosebolusservice.controller.dto.BolusDTO;
import com.diabeaten.glucosebolusservice.controller.interfaces.IBolusController;
import com.diabeaten.glucosebolusservice.model.Bolus;
import com.diabeaten.glucosebolusservice.service.BolusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class BolusController implements IBolusController {
    @Autowired
    private BolusService bolusService;

    @GetMapping("/bolus/by")
    @Override
    public List<Bolus> getBy(@RequestParam(name = "userId") Long userId, @RequestParam(name = "fromDate", required = false) Date date) {
        if (date == null) {
            return bolusService.getByUserId(userId);
        } else {
            return bolusService.getByUserAndPeriod(userId, date);
        }
    }

    @PostMapping("/bolus")
    @Override
    public Bolus create(@RequestBody @Valid BolusDTO bolusDTO) {
        return bolusService.create(bolusDTO);
    }



}
