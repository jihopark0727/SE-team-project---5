package com.example.demo.Service.Interface;

import com.example.demo.Entity.User;

import java.util.List;

public interface IUserService {
    User findById(String id);
    boolean isUserPL(String userId);
    List<User> getAllUsers();
    List<User> getDevsByProjectIdOrderByCareerDesc(Long projectId);
}
