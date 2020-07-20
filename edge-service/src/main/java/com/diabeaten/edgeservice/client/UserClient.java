package com.diabeaten.edgeservice.client;

import com.diabeaten.edgeservice.client.dto.NewUserDTO;
import com.diabeaten.edgeservice.model.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name ="user-service")
public interface UserClient {

    @GetMapping("/users")
    public List<User> getAll(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/users/{username}")
    public User findByUsername(@RequestHeader(name = "Authorization") String token, @PathVariable(name = "username") String username);

    @PostMapping("/users")
    public User createUser(@RequestHeader(name = "Authorization") String token, @RequestBody NewUserDTO newUserDTO);
}
