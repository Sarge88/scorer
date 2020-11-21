package com.gkalapis.scorer.services.user;

public interface UserService {

    String createUserIfNotExists(String name, String password);
}