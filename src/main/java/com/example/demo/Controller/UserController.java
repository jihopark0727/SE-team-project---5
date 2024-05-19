package com.example.demo.Controller;

import com.example.demo.DTO.LoginDto;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        String userType = (String) session.getAttribute("userType");
        if (userId == null || userType == null) {
            return ResponseEntity.status(401).body("No active session found");
        }
        // JSON 형태로 응답
        return ResponseEntity.ok().body(Map.of("user_id", userId, "user_type", userType));
    }

}
