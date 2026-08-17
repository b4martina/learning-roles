package com.example.AuthLearn.service;


import com.example.AuthLearn.dto.RegisterRequest;
import com.example.AuthLearn.dto.RegisterResponse;
import com.example.AuthLearn.model.Roles;
import com.example.AuthLearn.model.User;
import com.example.AuthLearn.repository.RoleRepository;
import com.example.AuthLearn.repository.UserRepository;
import com.example.AuthLearn.security.JWTGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JWTGenerator jwtGenerator;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, RoleRepository roleRepository, JWTGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator=jwtGenerator;
    }

 // System.out.println("REGISTER SERVICE HIT");

    public RegisterResponse register (RegisterRequest registerRequest){

        //System.out.println("REGISTER SERVICE HIT");
        if (userRepository.existsByUsername(registerRequest.getUsername())){
        throw new IllegalArgumentException("USername already exists");

    }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode((registerRequest.getPassword())));
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());


        Roles roles = roleRepository.findByRoleName("USER")
                                .orElseThrow(() -> new RuntimeException("USER role not found"));

        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        String token = jwtGenerator.generateToken(user.getUsername());
      //  System.out.println("TOKEN = " + token);
        return new RegisterResponse(token);


    }



}
