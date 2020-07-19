package com.diabeaten.edgeservice.service;

import com.diabeaten.edgeservice.client.UserClient;
import com.diabeaten.edgeservice.exception.UserClientNotWorkingException;
import com.diabeaten.edgeservice.model.dto.User;
import com.diabeaten.edgeservice.security.CustomSecuredUser;
import com.diabeaten.edgeservice.util.JwtUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserClient userClient;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    @HystrixCommand(fallbackMethod = "notLoadByUsername")
    public UserDetails loadUserByUsername(String username) {
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");
        System.out.println("Search user with username: " + username);
        try {
            User user = userClient.findByUsername(userToken, username);
            System.out.println(user);
            return new CustomSecuredUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UsernameNotFoundException("Invalid username/password combination.");
        }

                /*new CustomSecuredUser(user.orElseThrow(() ->
                new UsernameNotFoundException("Invalid username/password combination.")));*/
    }


    public UserDetails notLoadByUsername(String username) {
        throw new UserClientNotWorkingException("user-service not available!");
    }

    @Secured("ROLE_ADMIN")
    @HystrixCommand(fallbackMethod = "notGetAllUsers")
    public List<User> getAllUsers() {
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");
        return userClient.getAll(userToken);
    }

    public List<User> notGetAllUsers() {
        throw new UserClientNotWorkingException("user-service not available!");
    }
}
