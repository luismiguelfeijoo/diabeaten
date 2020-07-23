package com.diabeaten.edgeservice.controller.interfaces;

import com.diabeaten.edgeservice.client.dto.NewMonitorDTO;
import com.diabeaten.edgeservice.model.User;

import java.util.List;

public interface IMonitorController {
    public List<User> getAll();
    public User getById(Long id);
    public User create(NewMonitorDTO newMonitorDTO);
}
