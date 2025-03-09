package com.app1.service;

import com.app1.entity.User;
import com.app1.payload.LoginDto;
import com.app1.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private JWTService jwtService;

    public UserService(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public String verifyLogin(LoginDto dto)   {                                   // here login will get verify via boolean
        Optional<User> opUser = userRepository.findByUsername(dto.getUsername());
        if(opUser.isPresent()){
            User user = opUser.get();
            if(BCrypt.checkpw(dto.getPassword(), user.getPassword()));  //here we compare password we got in DTO to user entity password
             return  jwtService.generateToken(user.getUsername());  //if password will be true so we will make call to jwt service for generating token
        }
        return null;
    }
}
