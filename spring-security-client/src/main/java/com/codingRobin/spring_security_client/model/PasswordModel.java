package com.codingRobin.spring_security_client.model;

import lombok.Data;

@Data
public class PasswordModel {
    private String Email;
    private String oldPassword;
    private String newPassword;
}
