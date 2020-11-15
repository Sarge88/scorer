package com.gkalapis.scorer.controllers;

import com.gkalapis.scorer.domain.entities.User;
import com.gkalapis.scorer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gkalapis.scorer.services.user.UserService;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping("/users/create")
    public void create(String id, String name) {
        userService.createUserIfNotExists(id, name);
    }

    @RequestMapping("/users/findAll")
    public List<User> findAll(String status) { return userRepository.findAll(); }
}