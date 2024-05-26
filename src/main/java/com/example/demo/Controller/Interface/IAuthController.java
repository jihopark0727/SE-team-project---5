package com.example.demo.Controller.Interface;

import com.example.demo.DTO.LoginDto;
import com.example.demo.DTO.SignUpDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

public interface IAuthController {
    ResponseEntity<?> signUp(SignUpDto requestBody);
    ResponseEntity<?> login(LoginDto requestBody, HttpSession session);
    ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response);
}
