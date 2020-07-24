package com.diabeaten.notificationservice.client;

import com.diabeaten.notificationservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name ="user-service", url ="https://user-service-diabeaten.herokuapp.com")
public interface UserClient {

    @GetMapping("/patients/{id}")
    public User getPatientById(@RequestHeader(name = "Authorization") String token, @PathVariable(name = "id") Long id);

}
