package com.codingRobin.spring_security_client.controller;

import com.codingRobin.spring_security_client.entity.User;
import com.codingRobin.spring_security_client.entity.VerificationToken;
import com.codingRobin.spring_security_client.event.RegistrationCompleteEvent;
import com.codingRobin.spring_security_client.model.PasswordModel;
import com.codingRobin.spring_security_client.model.UserModel;
import com.codingRobin.spring_security_client.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request){
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Registration Successful!";
    }

    @GetMapping("verfiyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        String result = userService.validatVerificationToken(token);
        if (result.equalsIgnoreCase("valid")){
            return "User verification Successful!";
        }
        return "user verification failed!";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel){

        User user = UserService.findUserByEmail(passwordModel.getEmail());
        if( user != null){
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
        }
    }

    @GetMapping("/resendVerfiyToken")
    public String resendVerficationToken(@RequestParam("token") String oldToken, HttpServletRequest request){
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerficationTokenMail(user, applicationUrl(request), verificationToken);
        return "verification Link Sent";
    }

    private void resendVerficationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();
        log.info("click ink to verify account: {}", url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
    }
}
