package com.example.demo.Service;


import com.example.demo.DTO.LoginResponseDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.DTO.SignUpDto;
import com.example.demo.DTO.LoginDto;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired UserRepository userRepository;
    public ResponseDto<?> signUp(SignUpDto dto) {
        String id = dto.getId();
        String password = dto.getPassword();
        String confirmPassword = dto.getConfirm_password();

        // email(id) 중복 확인
        try {
            // 존재하는 경우 : true / 존재하지 않는 경우 : false
            if(userRepository.existsById(id)) {
                return ResponseDto.setFailed("중복된 Email 입니다.");
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
        }

        // password 중복 확인
        if(password.equals(confirmPassword)) {
            return ResponseDto.setFailed("비밀번호가 일치하지 않습니다.");
        }

        // UserEntity 생성
        User user = new User(dto);

        // UserRepository를 이용하여 DB에 Entity 저장 (데이터 적재)
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
        }

        return ResponseDto.setSuccess("회원 생성에 성공했습니다.");
    }
    public ResponseDto<LoginResponseDto> login(LoginDto dto) {
        String id = dto.getId();
        String password = dto.getPassword();

        try {
            // 사용자 id/password 일치하는지 확인
            boolean existed = userRepository.existsByIdAndPassword(id, password);
            if(!existed) {
                return ResponseDto.setFailed("입력하신 로그인 정보가 존재하지 않습니다.");
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
        }

        User user = null;

        try {
            // 값이 존재하는 경우 사용자 정보 불러옴 (기준 email)
            user = userRepository.findById(id).get();
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
        }

        user.setPassword("");

        String token = "";
        int exprTime = 3600000;     // 1h

        LoginResponseDto loginResponseDto = new LoginResponseDto(token, exprTime, user);

        return ResponseDto.setSuccessData("로그인에 성공하였습니다.", loginResponseDto);
    }

    public ResponseDto<?> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // 세션 무효화 후 클라이언트 측 쿠키 삭제
        jakarta.servlet.http.Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // 쿠키 삭제
        response.addCookie(cookie);
        return ResponseDto.setSuccess("로그아웃에 성공하였습니다.");
    }
}