package com.example.demo.Controller;

import com.example.demo.Controller.Interface.IAuthController;
import com.example.demo.DTO.*;
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
    public ResponseDto<?> signUp(@RequestBody SignUpDto requestBody) {
        System.out.println(requestBody.getId());
        System.out.println(requestBody.getPassword());
        ResponseDto<?> result = authService.signUp(requestBody);
        System.out.println(result.isResult());
        return result;
//        HttpHeaders headers = new HttpHeaders();
//        if (result.isResult()) {
//            headers.setLocation(URI.create("/index.html?signup=success")); // 회원가입 후 로그인 화면으로 리다이렉트
//        } else {
//            headers.setLocation(URI.create("/signup.html?error=true")); // 회원가입 실패 시 에러 메시지와 함께 회원가입 화면으로 리다이렉트
//        }
//        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @Override
    @PostMapping("/login")
    public ResponseDto<User> login(@RequestBody LoginDto requestBody, HttpSession session) {
        ResponseDto<User> result = authService.login(requestBody);
        System.out.println(result.isResult());
        if (result.isResult()) {
            User user = result.getData();
            System.out.println("User found: " + user.getId());  // 로그 추가
            session.setAttribute("userId", user.getId());
            session.setAttribute("userType", user.getUser_type());
            return ResponseDto.setSuccessData("found user", user);
        } else {
            return ResponseDto.setFailed("login failed");
        }
    }


    @Override
    @PostMapping("/logout")
    public ResponseDto<?> logout(HttpServletRequest request, HttpServletResponse response) {
        ResponseDto<?> result = authService.logout(request, response);
        return result;
    }
    @Override
    @PostMapping("/goresetpassword")
    public ResponseEntity<?> findPassword(@RequestBody ForgotPasswordRequestDto request) {
        ForgotPasswordResponseDto response = authService.findPassword(request.getEmail(), request.getName(), request.getTel());
        if (response != null) {
            return ResponseEntity.ok(ResponseDto.setSuccessData("Password found", response));
        } else {
            return ResponseEntity.badRequest().body(ResponseDto.setFailed("No matching user found"));
        }
    }
}