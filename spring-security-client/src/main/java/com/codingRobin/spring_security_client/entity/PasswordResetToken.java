package com.codingRobin.spring_security_client.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class PasswordResetToken {
    private static final int EXP_TIME = 10;//    10 to 15min exoiration date
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String token;
    private Date expirationTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    private User user;

    public PasswordResetToken(String token){
        super();
        this.token = token;
        this.expirationTime = calcExpirationTime(EXP_TIME);
    }

    public PasswordResetToken(String token, User user){
        super();
        this.token = token;
        this.user = user;
        this.expirationTime = calcExpirationTime(EXP_TIME);
    }

    private Date calcExpirationTime(int expTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expTime);
        return new Date(calendar.getTime().getTime());
    }
}
