package com.example.demo.Service;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    // 사용자가 프로젝트 리더인지 확인하는 메서드
    public boolean isUserPL(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }
        return user.getUser_type().equals("pl");  // user_type이 'pl'인지 확인
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getDevsByProjectIdOrderByCareerDesc(Long projectId) {
        return userRepository.findDevsByProjectIdOrderByCareerDesc(projectId);
    }
}
