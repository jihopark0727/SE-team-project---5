package com.example.demo.Controller;

import com.example.demo.Controller.Interface.IAuthController;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.DTO.SignUpDto;
import com.example.demo.DTO.LoginDto;
import com.example.demo.Entity.User;
import com.example.demo.Service.AuthService;
import com.example.demo.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements IAuthController {

    @Autowired
    AuthService authService;

    @Override
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

    @Autowired
    private UserService userService;
    @Override
    @PostMapping("/login")
    public ResponseEntity<?> login(@ModelAttribute LoginDto requestBody, HttpSession session) {
        ResponseDto<?> result = authService.login(requestBody);
        HttpHeaders headers = new HttpHeaders();
        if (result.isResult()) {
            User user = userService.findById(requestBody.getId());
            if (user != null) {
                System.out.println("User found: " + user.getId());  // 로그 추가
                session.setAttribute("userId", user.getId());
                session.setAttribute("userType", user.getUser_type());
                headers.setLocation(URI.create("/home.html?login=success"));
                return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
            } else {
                headers.setLocation(URI.create("/index.html?error=true")); // 로그인 실패 시 쿼리 파라미터 추가
                return new ResponseEntity<>(headers, HttpStatus.FOUND);
            }
        } else {
            headers.setLocation(URI.create("/index.html?error=true")); // 로그인 실패 시 쿼리 파라미터 추가
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
    }


    @Override
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        ResponseDto<?> result = authService.logout(request, response);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/index.html?logout=success")); // 로그아웃 후 로그인 화면으로 리다이렉트
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}