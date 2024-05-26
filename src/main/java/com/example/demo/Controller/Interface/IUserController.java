package com.example.demo.Controller.Interface;

import com.example.demo.Entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IUserController {
    List<User> getAllUsers();
    ResponseEntity<?> getUserProfile(HttpSession session);
    List<User> getDevsByProjectId(Long projectId);
    ResponseEntity<?> assignDevToIssue(Long issueId, Map<String, String> request);
}
