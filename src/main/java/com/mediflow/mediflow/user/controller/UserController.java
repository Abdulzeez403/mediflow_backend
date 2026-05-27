package com.mediflow.mediflow.user.controller;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediflow.mediflow.user.service.UserService;
import com.mediflow.mediflow.user.dto.UserDTO;

@RestController
@RequestMapping("/user")
public class UserController {
// Service
    private final UserService service;


    // Constructor
    public UserController(UserService service) {
        this.service = service;
    }

    // Endpoints
    @GetMapping("/all")
    public String getUsers() {
        return service.getUsers().toString();
    }


    @GetMapping("/me")
    public UserDTO getUserByEmail() {
        return service.getUserByEmail();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable UUID id) {
        return service.getUserById(id);
    }

    @PutMapping("/update")
    public UserDTO updateUser() {
        return service.updateUser();
    }

    @DeleteMapping("/delete")
    public String deleteUser() {
        return service.deleteUser();
    }

}