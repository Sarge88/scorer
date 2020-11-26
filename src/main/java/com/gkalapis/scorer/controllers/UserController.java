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
    public String create(String name, String password) throws Exception {
        System.out.println("creation of "+ name + " is started");
        return userService.createUserIfNotExists(name, password);
    }

    @RequestMapping("/users/restore")
    public String restore(String name, String password) throws Exception {
        System.out.println("restoration of "+ name + " is started");

        return userService.restoreUserIfExists(name, password);
    }

    @RequestMapping("/users/findAll")
    public List<User> findAll(String status) { return userRepository.findAll(); }
}