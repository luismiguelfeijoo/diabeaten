package com.diabeaten.userservice.service;

import com.diabeaten.userservice.controller.dto.NewUserDTO;
import com.diabeaten.userservice.exceptions.DuplicatedUsernameException;
import com.diabeaten.userservice.exceptions.NoSuchUserException;
import com.diabeaten.userservice.model.Role;
import com.diabeaten.userservice.model.User;
import com.diabeaten.userservice.repository.RoleRepository;
import com.diabeaten.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NoSuchUserException("There's no user with provided username"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User create(NewUserDTO user) {
        User newUser = new User(user.getUsername(), user.getPassword());
        Role role = new Role();
        role.setRole("ROLE_" + user.getType().toString());
        role.setUser(newUser);
        User result = null;
        try {
            result = userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedUsernameException("There's already an user with the provided username");
        }
        roleRepository.save(role);
        return result;
    }
}
