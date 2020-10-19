package com.gkalapis.scorer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gkalapis.scorer.services.user.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/create")
    public void create(String id, String name) {
        userService.createUserIfNotExists(id, name);
    }
}