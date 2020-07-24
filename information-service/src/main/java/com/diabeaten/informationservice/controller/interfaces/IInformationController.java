package com.diabeaten.informationservice.controller.interfaces;

import com.diabeaten.informationservice.controller.dto.InformationDTO;
import com.diabeaten.informationservice.controller.dto.UpdateInformationDTO;
import com.diabeaten.informationservice.model.Information;

public interface IInformationController {
    public Information getById(Long id);
    public Information create(InformationDTO informationDTO);
    public Information update(Long id, UpdateInformationDTO updateInformationDTO);
}
