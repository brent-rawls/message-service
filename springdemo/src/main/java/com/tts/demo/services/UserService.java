package com.tts.demo.services;

import com.tts.demo.entities.RoleEntity;
import com.tts.demo.entities.UserEntity;
import com.tts.demo.repositories.RoleRepository;
import com.tts.demo.repositories.UserRepository;
//import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserEntity> findAll() {
        return (List<UserEntity>) userRepository.findAll();
    }

    public void save(UserEntity user) {
        userRepository.save(user);
    }

    public UserEntity create(UserEntity newUser) {
        
    	String encryptedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encryptedPassword);
        
        newUser.setActive(1);
        RoleEntity role = roleRepository.findByRole("USER");
        newUser.setRoles(new HashSet<RoleEntity>(Arrays.asList(role)));

        return userRepository.save(newUser);
    }

    public UserEntity getLoggedInUser() {
        String loggedInUsername = SecurityContextHolder.
                getContext().getAuthentication().getName();

        return findByUsername(loggedInUsername);
    }
}
