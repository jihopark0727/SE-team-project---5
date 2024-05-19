package com.example.demo.Service;

import com.example.demo.Entity.UserEntity;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public String getUserType(String id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        return (user != null) ? user.getUser_type() : null;
    }
}
