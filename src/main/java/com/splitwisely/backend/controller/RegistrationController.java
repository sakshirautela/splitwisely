package com.splitwisely.backend.controller;

import com.splitwisely.backend.Util.JwtTokenUtil;
import com.splitwisely.backend.model.User;
import com.splitwisely.backend.repository.UserRepository;
import com.splitwisely.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/req/signup", consumes = "application/json")
    public ResponseEntity<String> createUser(@RequestBody User user) {

        Optional<User> existingAppUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));

        // USER ALREADY EXISTS
        if (existingAppUser.isPresent()) {
            User existingUser = existingAppUser.get();

            // Already verified user
            if (existingUser.isVerified()) {
                return new ResponseEntity<>("User already exists and verified.", HttpStatus.BAD_REQUEST);
            }

            // Exists but not verified → resend email
            String token = JwtTokenUtil.generateToken(existingUser.getEmail());
            existingUser.setVerificationToken(token);
            userRepository.save(existingUser);

            emailService.sendVerificationEmail(existingUser.getEmail(), token);

            return new ResponseEntity<>("Verification email resent. Check your inbox.", HttpStatus.OK);
        }

        // NEW USER FLOW
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerified(false);

        String token = JwtTokenUtil.generateToken(user.getEmail());
        user.setVerificationToken(token);

        userRepository.save(user);

        emailService.sendVerificationEmail(user.getEmail(), token);

        return new ResponseEntity<>("Registration successful! Please verify your email.", HttpStatus.OK);
    }
}
