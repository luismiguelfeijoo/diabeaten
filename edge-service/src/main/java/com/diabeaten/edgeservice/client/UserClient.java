package com.diabeaten.edgeservice.client;

import com.diabeaten.edgeservice.client.dto.NewMonitorDTO;
import com.diabeaten.edgeservice.client.dto.NewUserDTO;
import com.diabeaten.edgeservice.client.dto.UpdateUserDTO;
import com.diabeaten.edgeservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name ="user-service",url ="https://user-service-diabeaten.herokuapp.com" )
public interface UserClient {

    @GetMapping("/users")
    public List<User> getAll(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/users/get-by")
    public User getBy(@RequestHeader(name = "Authorization") String token, @RequestParam(name = "username", required = false) String username, @RequestParam(name = "id", required = false) Long id);

    /*
    @PostMapping("/users")
    public User createUser(@RequestHeader(name = "Authorization") String token, @RequestBody NewUserDTO newUserDTO);
     */


    @GetMapping("/patients")
    public List<User> getPatients(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/patients/{id}")
    public User getPatientById(@RequestHeader(name = "Authorization") String token, @PathVariable(name = "id") Long id);

    @PutMapping("/patients/{id}")
    public User updatePatient(@RequestHeader(name = "Authorization") String token, @PathVariable(name = "id") Long id, @RequestBody UpdateUserDTO updateUserDTO);

    @DeleteMapping("/patients/{id}")
    public void deletePatientById(@RequestHeader(name = "Authorization") String token, @PathVariable(name = "id") Long id);

    @PostMapping("/patients")
    public User createPatient(@RequestHeader(name = "Authorization") String token, @RequestBody NewUserDTO newUserDTO);

    @GetMapping("/monitors")
    public List<User> getMonitors(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/monitors/{id}")
    public User getMonitorById(@RequestHeader(name = "Authorization") String token, @PathVariable(name = "id") Long id);

    @DeleteMapping("/monitors/{id}")
    public User deleteMonitorById(@RequestHeader(name = "Authorization") String token, @PathVariable(name = "id") Long id);

    @PostMapping("/monitors")
    public User create(@RequestHeader(name = "Authorization") String token, @RequestBody NewMonitorDTO newMonitorDTO);
}
