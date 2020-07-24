package com.diabeaten.edgeservice.controller.impl;

import com.diabeaten.edgeservice.client.dto.NewMonitorDTO;
import com.diabeaten.edgeservice.controller.interfaces.IMonitorController;
import com.diabeaten.edgeservice.model.User;
import com.diabeaten.edgeservice.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MonitorController implements IMonitorController {
    @Autowired
    private MonitorService monitorService;

    @GetMapping("/monitors")
    @Override
    public List<User> getAll() {
        return monitorService.getAll();
    }

    @GetMapping("/monitors/{id}")
    @Override
    public User getById(@PathVariable(name = "id") Long id) {
        return monitorService.getById(id);
    }

    @DeleteMapping("/monitors/{id}")
    @Override
    public void delete(@PathVariable(name = "id") Long id) {
        monitorService.delete(id);
    }

    @PostMapping("/monitors")
    @Override
    public User create(@RequestBody @Valid NewMonitorDTO newMonitorDTO) {
        return monitorService.create(newMonitorDTO);
    }
}
