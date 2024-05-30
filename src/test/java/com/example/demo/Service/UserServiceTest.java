package com.example.demo.Service;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        User user = new User();
        user.setId("1");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        User foundUser = userService.findById("1");
        assertNotNull(foundUser);
        assertEquals("1", foundUser.getId());
    }

    @Test
    void testIsUserPL_UserIsPL() {
        User user = new User();
        user.setId("1");
        user.setUser_type("pl");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        boolean isPL = userService.isUserPL("1");
        assertTrue(isPL);
    }

    @Test
    void testIsUserPL_UserIsNotPL() {
        User user = new User();
        user.setId("1");
        user.setUser_type("dev");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        boolean isPL = userService.isUserPL("1");
        assertFalse(isPL);
    }

    @Test
    void testIsUserPL_UserNotFound() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        boolean isPL = userService.isUserPL("1");
        assertFalse(isPL);
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setId("1");
        User user2 = new User();
        user2.setId("2");
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<User> foundUsers = userService.getAllUsers();
        assertEquals(2, foundUsers.size());
    }

    @Test
    void testGetDevsByProjectIdOrderByCareerDesc() {
        User user1 = new User();
        user1.setId("1");
        user1.setCareer(5);
        User user2 = new User();
        user2.setId("2");
        user2.setCareer(10);
        List<User> users = Arrays.asList(user2, user1);
        when(userRepository.findDevsByProjectIdOrderByCareerDesc(1L)).thenReturn(users);

        List<User> foundUsers = userService.getDevsByProjectIdOrderByCareerDesc(1L);
        assertEquals(2, foundUsers.size());
        assertEquals(10, foundUsers.get(0).getCareer());
    }
}
