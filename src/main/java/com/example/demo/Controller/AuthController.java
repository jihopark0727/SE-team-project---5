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
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import java.net.URI;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid @ModelAttribute SignUpDto requestBody, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessages = bindingResult.getFieldErrors().stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("{\"error\":true, \"messages\":\"" + errorMessages + "\"}");
        }

        ResponseDto<?> result = authService.signUp(requestBody);
        if (result.isResult()) {
            return ResponseEntity.ok().body("{\"success\":true, \"message\":\"회원가입 성공!\"}");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":true, \"messages\":\"회원가입 실패, 서버 내부 오류.\"}");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@ModelAttribute LoginDto requestBody) {
        ResponseDto<?> result = authService.login(requestBody);
        HttpHeaders headers = new HttpHeaders();
        if (result.isResult()) {
            headers.setLocation(URI.create("/home.html?login=success"));
        } else {
            headers.setLocation(URI.create("/index.html?error=true")); // 로그인 실패 시 에러 메시지와 함께 로그인 화면으로 리다이렉트
        }
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
