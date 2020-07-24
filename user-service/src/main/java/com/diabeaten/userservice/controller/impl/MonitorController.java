package com.diabeaten.userservice.controller.impl;

import com.diabeaten.userservice.controller.dto.NewMonitorDTO;
import com.diabeaten.userservice.controller.interfaces.IMonitorController;
import com.diabeaten.userservice.model.Monitor;
import com.diabeaten.userservice.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MonitorController implements IMonitorController {
    @Autowired
    private MonitorService monitorService;

    @GetMapping("/monitors")
    @Override
    public List<Monitor> getMonitors() {
        return monitorService.getAll();
    }

    @GetMapping("/monitors/{id}")
    @Override
    public Monitor getById(@PathVariable(name = "id") Long id) {
        return monitorService.getById(id);
    }

    @PostMapping("/monitors")
    @Override
    public Monitor create(@RequestBody NewMonitorDTO newMonitorDTO) {
        return monitorService.create(newMonitorDTO);
    }

    @DeleteMapping("/monitors/{id}")
    @Override
    public void delete(@PathVariable(name = "id") Long id) {
        monitorService.delete(id);
    }
}
