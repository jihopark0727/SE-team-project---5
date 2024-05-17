package com.example.demo.Controller;

import com.example.demo.DTO.ResponseDto;
import com.example.demo.DTO.SignUpDto;
import com.example.demo.DTO.LoginDto;
import com.example.demo.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@ModelAttribute SignUpDto requestBody) {
        ResponseDto<?> result = authService.signUp(requestBody);
        HttpHeaders headers = new HttpHeaders();
        if (result.isResult()) {
            headers.setLocation(URI.create("/index.html?signup=success")); // 회원가입 후 로그인 화면으로 리다이렉트
        } else {
            headers.setLocation(URI.create("/signup.html?error=true")); // 회원가입 실패 시 에러 메시지와 함께 회원가입 화면으로 리다이렉트
        }
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@ModelAttribute LoginDto requestBody) {
        ResponseDto<?> result = authService.login(requestBody);
        HttpHeaders headers = new HttpHeaders();
        if (result.isResult()) {
            headers.setLocation(URI.create("/home.html?login=success"));
        } else {
            headers.setLocation(URI.create("/index.html?error=true")); // 로그인 실패 시 쿼리 파라미터 추가
        }
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}