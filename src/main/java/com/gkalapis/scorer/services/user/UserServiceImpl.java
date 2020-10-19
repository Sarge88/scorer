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
    public void createUserIfNotExists(String id, String name) {
        if (!userRepository.existsById(id)) {
            User newUser = new User(id, name);
            userRepository.save(newUser);
        }
    }
}