package com.example.AuthLearn.service;


import com.example.AuthLearn.model.Roles;
import com.example.AuthLearn.model.User;
import com.example.AuthLearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//security 2
@Service
public class CustomUserDetailService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailService (UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("user not found"));

        return new org.springframework.security.core.userdetails.User (
                user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities () {
        return List.of();
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities (List<Roles> roles){

        if (roles == null) {
            return List.of();
        }
        return roles.stream().map(role ->  (GrantedAuthority) new SimpleGrantedAuthority(role.getRoleName()))
                .toList();

    }

}
