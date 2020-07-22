package com.diabeaten.informationservice.controller.impl;

import com.diabeaten.informationservice.controller.dto.InformationDTO;
import com.diabeaten.informationservice.controller.dto.RatioDTO;
import com.diabeaten.informationservice.controller.dto.UpdateInformationDTO;
import com.diabeaten.informationservice.controller.interfaces.IInformationController;
import com.diabeaten.informationservice.model.Information;
import com.diabeaten.informationservice.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class InformationController implements IInformationController {
    @Autowired
    private InformationService informationService;

    @GetMapping("/information/{id}")
    @Override
    public Information getById(@PathVariable(name = "id") Long id) {
        return informationService.getById(id);
    }

    @PutMapping("/information/{id}")
    @Override
    public Information update(@PathVariable(name = "id") Long id, @RequestBody UpdateInformationDTO updateInformationDTO) {
        return informationService.update(id, updateInformationDTO);
    }

    @PostMapping("/information")
    @Override
    public Information create(@RequestBody @Valid InformationDTO informationDTO) {
        return informationService.create(informationDTO);
    }

    /*
    @GetMapping("/information/{id}/ratios")
    public void createRatios(@RequestBody RatioDTO ratioDTO) {

    }
     */
}
