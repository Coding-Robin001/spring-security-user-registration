package com.codingRobin.spring_security_client.repository;

import com.codingRobin.spring_security_client.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
