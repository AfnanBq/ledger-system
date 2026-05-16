package com.example.app.controller;

import com.example.app.model.dto.CreateUserRequest;
import com.example.app.model.dto.UserBasic;
import com.example.app.service.UsersService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<UserBasic> createUser(@Valid @RequestBody CreateUserRequest user) {
        UserBasic savedUsers = usersService.addUser(user);
        return ResponseEntity.ok(savedUsers);
    }

    @GetMapping("/{id}")
    public UserBasic findUserById(@PathVariable Long id) {
        return usersService.getUserById(id);
    }

    @GetMapping("/")
    public Page<UserBasic> getAllUsers(Pageable pageable) {
        return usersService.getAllUsers(pageable);
    }
}
