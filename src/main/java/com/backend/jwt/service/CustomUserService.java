package com.backend.jwt.service;

import com.backend.jwt.Entities.User;
import com.backend.jwt.reposirtory.userDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    userDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDetailsRepository.findByUserName(username);
        if( null== user){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
