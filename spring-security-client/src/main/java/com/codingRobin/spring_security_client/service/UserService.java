package com.codingRobin.spring_security_client.service;

import com.codingRobin.spring_security_client.entity.User;
import com.codingRobin.spring_security_client.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerficationTokenForUser(String token, User user);

    String validateVerficationToken(String token);

    String validatVerificationToken(String token);
}
