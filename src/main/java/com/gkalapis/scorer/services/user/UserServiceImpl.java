package com.gkalapis.scorer.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gkalapis.scorer.domain.entities.User;
import com.gkalapis.scorer.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public String createUserIfNotExists(String name, String password) {
        User user = userRepository.findByName(name);
        if (user == null) {
            User newUser = new User(name, password);
            userRepository.save(newUser);
            return "User is successfully created: " + newUser.getName();
        }
        else{
            return user.getName() + " already exists!";
        }
    }
}