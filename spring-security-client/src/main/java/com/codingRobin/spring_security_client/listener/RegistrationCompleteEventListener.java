package com.codingRobin.spring_security_client.listener;


import com.codingRobin.spring_security_client.entity.User;
import com.codingRobin.spring_security_client.event.RegistrationCompleteEvent;
import com.codingRobin.spring_security_client.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import java.util.UUID;

@Slf4j
public class RegistrationCompleteEventListener implements
        ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerficationTokenForUser(token, user);

//        send mail to user
        String url = event.getApplicationUrl() + "verifyRegistration?token=" + token;
    }
}
