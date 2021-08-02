package com.example.RedditBackend.service;


import com.example.RedditBackend.dto.RegisterRequest;
import com.example.RedditBackend.model.User;
import com.example.RedditBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public void  signup(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now()); // current timing
        user.setEnabled(false);

        userRepository.save(user);

        generateVerificationToken(user);

    }
    private void generateVerificationToken(User user){
        String VerificationToken  = UUID.randomUUID().toString();

    }
}
