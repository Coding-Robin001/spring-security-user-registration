package com.codingRobin.spring_security_client.listener;


import com.codingRobin.spring_security_client.entity.User;
import com.codingRobin.spring_security_client.event.RegistrationCompleteEvent;
import org.springframework.context.ApplicationListener;

import java.util.UUID;

public class RegistrationCompleteEventListener implements
        ApplicationListener<RegistrationCompleteEvent> {
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        User user = event.getUser();
        String token = UUID.randomUUID().toString();
    }
}
