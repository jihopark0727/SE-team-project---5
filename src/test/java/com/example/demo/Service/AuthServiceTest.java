package com.example.demo.Service;


import com.example.demo.DTO.*;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTest {


    @Autowired
    private AuthService authService;


    @MockBean
    private UserRepository userRepository;


    @MockBean
    private HttpServletRequest request;


    @MockBean
    private HttpServletResponse response;


    @MockBean
    private HttpSession session;


    private SignUpDto signUpDto;
    private LoginDto loginDto;
    private User user;


    @Before
    public void setUp() {
        signUpDto = new SignUpDto();
        signUpDto.setId("test@example.com");
        signUpDto.setPassword("password123");
        signUpDto.setConfirm_password("password123");


        loginDto = new LoginDto();
        loginDto.setId("test@example.com");
        loginDto.setPassword("password123");


        user = new User(signUpDto);
        user.setId("test@example.com");
        user.setPassword("password123");
    }


    @Test
    public void testSignUpSuccess() {
        when(userRepository.existsById(signUpDto.getId())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);


        ResponseDto<?> response = authService.signUp(signUpDto);


        assertEquals("회원 생성에 성공했습니다.", response.getMessage());
    }


    @Test
    public void testSignUpDuplicateEmail() {
        when(userRepository.existsById(signUpDto.getId())).thenReturn(true);


        ResponseDto<?> response = authService.signUp(signUpDto);


        assertEquals("중복된 Email 입니다.", response.getMessage());
    }


    @Test
    public void testLoginSuccess() {
        when(userRepository.existsByIdAndPassword(loginDto.getId(), loginDto.getPassword())).thenReturn(true);
        when(userRepository.findById(loginDto.getId())).thenReturn(java.util.Optional.of(user));


        ResponseDto<User> response = authService.login(loginDto);


        assertEquals("로그인에 성공하였습니다.", response.getMessage());
        assertEquals(loginDto.getId(), response.getData().getId());
    }


    @Test
    public void testLoginFailure() {
        when(userRepository.existsByIdAndPassword(loginDto.getId(), loginDto.getPassword())).thenReturn(false);


        ResponseDto<User> response = authService.login(loginDto);


        assertEquals("입력하신 로그인 정보가 존재하지 않습니다.", response.getMessage());
    }


    @Test
    public void testLogoutSuccess() {
        when(request.getSession(false)).thenReturn(session);


        ResponseDto<?> logoutResponse = authService.logout(request, response);


        verify(session, times(1)).invalidate();
        assertEquals("로그아웃에 성공하였습니다.", logoutResponse.getMessage());
    }


    @Test
    public void testFindPasswordSuccess() {
        when(userRepository.findByIdAndNameAndTel("test@example.com", "Test User", "010-1234-5678"))
                .thenReturn(user);


        ForgotPasswordResponseDto response = authService.findPassword("test@example.com", "Test User", "010-1234-5678");


        assertNotNull(response);
        assertEquals("password123", response.getPassword());
    }


    @Test
    public void testFindPasswordFailure() {
        when(userRepository.findByIdAndNameAndTel("test@example.com", "Test User", "010-1234-5678"))
                .thenReturn(null);


        ForgotPasswordResponseDto response = authService.findPassword("test@example.com", "Test User", "010-1234-5678");


        assertNull(response);
    }
}
