package com.gkalapis.scorer.services.user;

public interface UserService {

    String createUserIfNotExists(String name, String password) throws Exception;
    String restoreUserIfExists(String name, String password) throws Exception;
}