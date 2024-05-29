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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements IAuthController {

    @Autowired
    AuthService authService;

    @Autowired
    private UserService userService;

    @Override
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

    @Override
    @PostMapping("/login")
    public ResponseEntity<?> login(@ModelAttribute LoginDto requestBody, HttpSession session) {
        ResponseDto<?> result = authService.login(requestBody);
        HttpHeaders headers = new HttpHeaders();
        if (result.isResult()) {
            User user = userService.findById(requestBody.getId());
            if (user != null) {
                session.setAttribute("userId", user.getId());
                session.setAttribute("userType", user.getUser_type());
                headers.setLocation(URI.create("/home.html?login=success"));
                return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
            } else {
                headers.setLocation(URI.create("/index.html?error=true"));
                return new ResponseEntity<>(headers, HttpStatus.FOUND);
            }
        } else {
            headers.setLocation(URI.create("/index.html?error=true"));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        ResponseDto<?> result = authService.logout(request, response);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/index.html?logout=success"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
