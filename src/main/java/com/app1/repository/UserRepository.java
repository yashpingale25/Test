package com.app1.repository;

import com.app1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmailId(String emailId);
    Optional<User> findByMobile(String mobile);
 }