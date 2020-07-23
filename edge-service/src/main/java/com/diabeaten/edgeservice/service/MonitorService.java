package com.diabeaten.edgeservice.service;

import com.diabeaten.edgeservice.client.UserClient;
import com.diabeaten.edgeservice.client.dto.NewMonitorDTO;
import com.diabeaten.edgeservice.model.User;
import com.diabeaten.edgeservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitorService {
    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtUtil jwtUtil;

    public List<User> getAll() {
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");
        return userClient.getMonitors(userToken);
    }

    public User getById(Long id) {
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");
        return userClient.getMonitorById(userToken, id);
    }

    public User create(NewMonitorDTO newMonitorDTO) {
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");
        return userClient.create(userToken, newMonitorDTO);
    }
}
