package com.example.demo.Controller;

import com.example.demo.Controller.Interface.IUserController;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.User;
import com.example.demo.Service.Interface.IUserService;
import com.example.demo.Service.IssueService; // 추가
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController implements IUserController {

    @Autowired
    private IUserService userService;


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        String userType = (String) session.getAttribute("userType");
        if (userId == null || userType == null) {
            return ResponseEntity.status(401).body("No active session found");
        }
        // JSON 형태로 응답
        return ResponseEntity.ok().body(Map.of("id", userId, "user_type", userType));
    }

    @Override
    @GetMapping("/{projectId}/devs")
    public List<User> getDevsByProjectId(@PathVariable Long projectId) {
        return userService.getDevsByProjectIdOrderByCareerDesc(projectId);
    }

}
