package com.example.demo.Service.Interface;

import com.example.demo.DTO.LoginDto;
import com.example.demo.DTO.LoginResponseDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.DTO.SignUpDto;
import com.example.demo.Entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
    ResponseDto<?> signUp(SignUpDto dto);
    ResponseDto<User> login(LoginDto dto);
    ResponseDto<?> logout(HttpServletRequest request, HttpServletResponse response);
}
