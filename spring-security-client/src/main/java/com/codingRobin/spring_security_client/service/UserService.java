package com.codingRobin.spring_security_client.service;

import com.codingRobin.spring_security_client.entity.User;
import com.codingRobin.spring_security_client.entity.VerificationToken;
import com.codingRobin.spring_security_client.model.UserModel;

public interface UserService {
    User findUserByEmail(String email);

    User registerUser(UserModel userModel);

    void saveVerficationTokenForUser(String token, User user);

    String validateVerficationToken(String token);

    String validatVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    void createPasswordResetTokenForUser(User user, String token);
}
