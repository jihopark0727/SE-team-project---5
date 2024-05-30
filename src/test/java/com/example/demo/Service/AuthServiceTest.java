package com.example.demo.Service;

import com.example.demo.DTO.*;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUp_Success() {
        SignUpDto signUpDto = new SignUpDto("user1", "John Doe", "password", "password", "1234567890", "user", 5, null, null, null, null);
        when(userRepository.existsById(signUpDto.getId())).thenReturn(false);

        ResponseDto<?> response = authService.signUp(signUpDto);
        assertEquals("회원 생성에 성공했습니다.", response.getMessage());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testSignUp_EmailExists() {
        SignUpDto signUpDto = new SignUpDto("user1", "John Doe", "password", "password", "1234567890", "user", 5, null, null, null, null);
        when(userRepository.existsById(signUpDto.getId())).thenReturn(true);

        ResponseDto<?> response = authService.signUp(signUpDto);
        assertEquals("중복된 Email 입니다.", response.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testSignUp_PasswordMismatch() {
        SignUpDto signUpDto = new SignUpDto("user1", "John Doe", "password", "differentPassword", "1234567890", "user", 5, null, null, null, null);

        ResponseDto<?> response = authService.signUp(signUpDto);
        assertEquals("비밀번호가 일치하지 않습니다.", response.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testLogin_Success() {
        LoginDto loginDto = new LoginDto("user1", "password");
        when(userRepository.existsByIdAndPassword(loginDto.getId(), loginDto.getPassword())).thenReturn(true);
        User user = new User();
        user.setId("user1");
        user.setPassword("password");
        user.setUser_type("user");
        when(userRepository.findById(loginDto.getId())).thenReturn(java.util.Optional.of(user));

        ResponseDto<User> response = authService.login(loginDto);
        assertEquals("로그인에 성공하였습니다.", response.getMessage());
        assertEquals("user1", response.getData().getId());
    }

    @Test
    public void testLogin_Failed() {
        LoginDto loginDto = new LoginDto("user1", "password");
        when(userRepository.existsByIdAndPassword(loginDto.getId(), loginDto.getPassword())).thenReturn(false);

        ResponseDto<User> response = authService.login(loginDto);
        assertEquals("입력하신 로그인 정보가 존재하지 않습니다.", response.getMessage());
    }

    @Test
    public void testLogout_Success() {
        when(request.getSession(false)).thenReturn(session);

        ResponseDto<?> response = authService.logout(request, this.response);
        assertEquals("로그아웃에 성공하였습니다.", response.getMessage());
        verify(session, times(1)).invalidate();
    }

    @Test
    public void testFindPassword_Success() {
        String email = "user1";
        String name = "John Doe";
        String tel = "1234567890";
        User user = new User();
        user.setId(email);
        user.setName(name);
        user.setTel(tel);
        user.setPassword("password");
        when(userRepository.findByIdAndNameAndTel(email, name, tel)).thenReturn(user);

        ForgotPasswordResponseDto response = authService.findPassword(email, name, tel);
        assertEquals("password", response.getPassword());
    }

    @Test
    public void testFindPassword_Failed() {
        String email = "user1";
        String name = "John Doe";
        String tel = "1234567890";
        when(userRepository.findByIdAndNameAndTel(email, name, tel)).thenReturn(null);

        ForgotPasswordResponseDto response = authService.findPassword(email, name, tel);
        assertEquals(null, response);
    }
}
