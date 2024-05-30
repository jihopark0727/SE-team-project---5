package com.example.demo.Controller.Interface;

import com.example.demo.DTO.ForgotPasswordRequestDto;
import com.example.demo.DTO.LoginDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.DTO.SignUpDto;
import com.example.demo.Entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

public interface IAuthController {
    ResponseDto<?> signUp(SignUpDto requestBody);
    ResponseDto<User> login(LoginDto requestBody, HttpSession session);
    ResponseDto<?> logout(HttpServletRequest request, HttpServletResponse response);
    ResponseEntity<?> findPassword(ForgotPasswordRequestDto request);
}
