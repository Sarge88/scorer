package com.gkalapis.scorer.services.user;

public interface UserService {

    void createUserIfNotExists(String id, String name);
}