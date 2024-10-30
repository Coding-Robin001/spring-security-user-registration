package com.codingRobin.spring_security_client.service;

import com.codingRobin.spring_security_client.entity.User;
import com.codingRobin.spring_security_client.entity.VerificationToken;
import com.codingRobin.spring_security_client.model.UserModel;
import com.codingRobin.spring_security_client.repository.UserRepository;
import com.codingRobin.spring_security_client.repository.VerficationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class UserServiceImplementation implements UserService{
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findbyEmail(email);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerficationTokenRepository verficationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()) );
        user.setRole("STUDENT");

        userRepository.save(user);
        return user;
    }

    @Override
    public void saveVerficationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(token, user);
        verficationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerficationToken(String token) {
        VerificationToken verificationToken = verficationTokenRepository.findByToken(token);
        if (verificationToken == null){
            return "Invalid Token";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if(verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0){
            verficationTokenRepository.delete(verificationToken);
            return "token expired!";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public String validatVerificationToken(String token) {
        VerificationToken verificationToken = verficationTokenRepository.findByToken(token);
        if (verificationToken == null){
            return "Invalid Token";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if(verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0){
            verficationTokenRepository.delete(verificationToken);
            return "token expired!";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verficationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verficationTokenRepository.save(verificationToken);
        return verificationToken;
    }
}
