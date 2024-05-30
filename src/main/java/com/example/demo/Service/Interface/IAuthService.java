package com.example.demo.Service.Interface;

import com.example.demo.DTO.*;
import com.example.demo.Entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
    ResponseDto<?> signUp(SignUpDto dto);
    ResponseDto<User> login(LoginDto dto);
    ResponseDto<?> logout(HttpServletRequest request, HttpServletResponse response);
    ForgotPasswordResponseDto findPassword(String id, String name, String tel);
}
