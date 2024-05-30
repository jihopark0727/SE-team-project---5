package com.example.demo.Service;

import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;
import com.example.demo.Repository.ProjectRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.Interface.IProjectService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllProjects_AdminUser() {
        User adminUser = new User();
        adminUser.setUser_type("admin");
        when(userRepository.findById("admin1")).thenReturn(Optional.of(adminUser));

        List<Project> projects = Arrays.asList(new Project(), new Project());
        when(projectRepository.findAll()).thenReturn(projects);

        List<Project> result = projectService.getAllProjects("admin1");

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllProjects_RegularUser() {
        User regularUser = new User();
        regularUser.setUser_type("user");
        when(userRepository.findById("user1")).thenReturn(Optional.of(regularUser));

        List<Project> projects = Arrays.asList(new Project(), new Project());
        when(projectRepository.findProjectsByUserId("user1")).thenReturn(projects);

        List<Project> result = projectService.getAllProjects("user1");

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(projectRepository, times(1)).findProjectsByUserId("user1");
    }

    @Test
    public void testGetAllProjects_UserNotFound() {
        when(userRepository.findById("user1")).thenReturn(Optional.empty());

        List<Project> result = projectService.getAllProjects("user1");

        assertNull(result);
        verify(projectRepository, times(0)).findAll();
        verify(projectRepository, times(0)).findProjectsByUserId("user1");
    }

    @Test
    public void testGetProjectById_ProjectExists() {
        Project project = new Project();
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Project result = projectService.getProjectById(1L);

        assertNotNull(result);
        assertEquals(project, result);
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetProjectById_ProjectNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        Project result = projectService.getProjectById(1L);

        assertNull(result);
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddProject() {
        Project project = new Project();
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project result = projectService.addProject(project);

        assertNotNull(result);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    @Transactional
    public void testAddUsersToProject() {
        Project project = new Project();
        project.setUsers(new HashSet<>());
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        User user1 = new User();
        user1.setId("user1");
        User user2 = new User();
        user2.setId("user2");

        Set<String> userIds = new HashSet<>(Arrays.asList("user1", "user2"));
        when(userRepository.findAllById(userIds)).thenReturn(Arrays.asList(user1, user2));

        projectService.addUsersToProject(1L, userIds);

        assertEquals(2, project.getUsers().size());
        verify(projectRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findAllById(userIds);
        verify(projectRepository, times(1)).save(project);
    }

    @Test(expected = RuntimeException.class)
    @Transactional
    public void testAddUsersToProject_ProjectNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        Set<String> userIds = new HashSet<>(Arrays.asList("user1", "user2"));

        projectService.addUsersToProject(1L, userIds);
    }

    @Test
    public void testGetUsersByProjectId() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findUsersByProjectId(1L)).thenReturn(users);

        List<User> result = projectService.getUsersByProjectId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findUsersByProjectId(1L);
    }
}
