package com.diabeaten.notificationservice.client;

import com.diabeaten.notificationservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name ="user-service")
public interface UserClient {

    @GetMapping("/patients")
    public List<User> getPatients(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/patients/{id}")
    public User getPatientById(@RequestHeader(name = "Authorization") String token, @PathVariable(name = "id") Long id);

}
