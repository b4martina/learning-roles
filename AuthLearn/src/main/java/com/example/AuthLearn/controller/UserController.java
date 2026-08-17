package com.example.AuthLearn.controller;


import com.example.AuthLearn.dto.RegisterRequest;
import com.example.AuthLearn.dto.RegisterResponse;
import com.example.AuthLearn.model.Roles;
import com.example.AuthLearn.model.User;
import com.example.AuthLearn.repository.RoleRepository;
import com.example.AuthLearn.repository.UserRepository;
import com.example.AuthLearn.security.JWTGenerator;
import com.example.AuthLearn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private AuthenticationManager authenticationManager;
    private UserService userService ;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
            private PasswordEncoder passwordEncoder;

            private JWTGenerator jwtGenerator;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator =jwtGenerator;
        this.userService =userService;
    }


    @PostMapping("/registere")
    public ResponseEntity <?> register (@RequestBody RegisterRequest registerRequest){
        System.out.println("REGISTER controller HIT");

     /* idk   RegisterResponse registerResponse = userService.register(registerRequest);
        return  ResponseEntity.ok(registerResponse);*/

        try {

            RegisterResponse registerResponse= userService.register(registerRequest);
            return ResponseEntity.ok(registerResponse);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
