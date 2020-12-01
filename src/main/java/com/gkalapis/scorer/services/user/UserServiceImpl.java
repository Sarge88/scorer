package com.gkalapis.scorer.services.user;

import com.gkalapis.scorer.domain.entities.User;
import com.gkalapis.scorer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public String createUserIfNotExists(String name, String password) throws Exception {
        User user = userRepository.findByName(name);
        if (user == null) {
            User newUser = new User(name, password);
            userRepository.save(newUser);
            return "User is successfully created: " + newUser.getName();
        }
        else{
            throw new Exception("Something happened.");
        }
    }

    @Override
    public String restoreUserIfExists(String name, String password) throws Exception {
        User user = userRepository.findByName(name);
        if (user != null && user.getPassword().equals(password)) {
            return "User is successfully restored: " + name;
        }
        else{
            throw new Exception("User:" + name + " does not exist");
        }
    }
}