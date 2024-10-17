package com.codingRobin.spring_security_client.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String token;
    private Date expirationTime; //    10 to 15min exoiration date

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = 'user_id', nullable = false, foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    private User user;
}
