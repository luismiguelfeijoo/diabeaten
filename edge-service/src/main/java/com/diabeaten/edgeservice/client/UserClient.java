package com.diabeaten.edgeservice.client;

import com.diabeaten.edgeservice.client.dto.NewUserDTO;
import com.diabeaten.edgeservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name ="user-service")
public interface UserClient {

    @GetMapping("/users")
    public List<User> getAll(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/users")
    public User getBy(@RequestHeader(name = "Authorization") String token, @RequestParam(name = "username", required = false) String username, @RequestParam(name = "id", required = false) Long id);

    @PostMapping("/users")
    public User createUser(@RequestHeader(name = "Authorization") String token, @RequestBody NewUserDTO newUserDTO);
}
