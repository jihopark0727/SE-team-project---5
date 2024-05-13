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
    @Autowired AuthService authService;

    @PostMapping("/signUp")
    public ResponseDto<?> signUp(@ModelAttribute SignUpDto requestBody) {
        ResponseDto<?> result = authService.signUp(requestBody);
        return result;
    }

//    @PostMapping("/login")
//    public ResponseDto<?> login(@ModelAttribute LoginDto requestBody) {
//        ResponseDto<?> result = authService.login(requestBody);
//        return result;
//    }
    // 리다이렉트 로그인 성공하면 홈화면 실패하면 다시 로그인화면
    @PostMapping("/login")
    public ResponseEntity<?> login(@ModelAttribute LoginDto requestBody) {
        ResponseDto<?> result = authService.login(requestBody);
        HttpHeaders headers = new HttpHeaders();
        if(result.isResult()) {
            headers.setLocation(URI.create("/home.html"));
        } else {
            headers.setLocation(URI.create("/index.html?error=true")); // 로그인 실패 시 쿼리 파라미터 추가
        }
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}