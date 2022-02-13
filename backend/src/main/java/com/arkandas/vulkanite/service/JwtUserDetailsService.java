package com.arkandas.vulkanite.service;

import com.arkandas.vulkanite.model.db.User;
import com.arkandas.vulkanite.model.request.RegisterRequest;
import com.arkandas.vulkanite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public User save(RegisterRequest registerRequest) {
        User newUser = new User();
        newUser.setUserfullname(registerRequest.getFullname());
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(bcryptEncoder.encode(registerRequest.getPassword()));
        newUser.setUserEmail(registerRequest.getEmail());
        newUser.setUserType(1);

        return userRepository.save(newUser);
    }
}
