package com.example.demo.Service;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById_UserExists() {
        User user = new User();
        user.setId("user1");
        when(userRepository.findById("user1")).thenReturn(Optional.of(user));

        User result = userService.findById("user1");
        assertNotNull(result);
        assertEquals("user1", result.getId());
    }

    @Test
    public void testFindById_UserNotExists() {
        when(userRepository.findById("user1")).thenReturn(Optional.empty());

        User result = userService.findById("user1");
        assertNull(result);
    }

    @Test
    public void testIsUserPL_UserIsPL() {
        User user = new User();
        user.setId("user1");
        user.setUser_type("pl");
        when(userRepository.findById("user1")).thenReturn(Optional.of(user));

        boolean result = userService.isUserPL("user1");
        assertTrue(result);
    }

    @Test
    public void testIsUserPL_UserIsNotPL() {
        User user = new User();
        user.setId("user1");
        user.setUser_type("dev");
        when(userRepository.findById("user1")).thenReturn(Optional.of(user));

        boolean result = userService.isUserPL("user1");
        assertFalse(result);
    }

    @Test
    public void testIsUserPL_UserNotExists() {
        when(userRepository.findById("user1")).thenReturn(Optional.empty());

        boolean result = userService.isUserPL("user1");
        assertFalse(result);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId("user1");
        User user2 = new User();
        user2.setId("user2");
        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetDevsByProjectIdOrderByCareerDesc() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId("user1");
        User user2 = new User();
        user2.setId("user2");
        users.add(user1);
        users.add(user2);

        when(userRepository.findDevsByProjectIdOrderByCareerDesc(1L)).thenReturn(users);

        List<User> result = userService.getDevsByProjectIdOrderByCareerDesc(1L);
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
