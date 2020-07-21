package com.diabeaten.userservice.controller.interfaces;

import com.diabeaten.userservice.model.Monitor;

import java.util.List;

public interface IMonitorController {
    public List<Monitor> getMonitors();
    public Monitor getById(Long id);
}