package com.example.demo.Controller.Interface;

import com.example.demo.DTO.LoginDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.DTO.SignUpDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface IAuthController {
    ResponseEntity<?> signUp(@ModelAttribute SignUpDto requestBody, BindingResult bindingResult);
    ResponseEntity<?> login(@ModelAttribute LoginDto requestBody, HttpSession session);
    ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response);
}
