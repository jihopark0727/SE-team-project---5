package com.example.demo.Service.Interface;

import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;

import java.util.List;
import java.util.Set;

public interface IProjectService {
    List<Project> getAllProjects();
    Project getProjectById(Long projectId);
    Project addProject(Project project);
    void addUsersToProject(Long projectId, Set<String> userId);
    List<User> getUsersByProjectId(Long projectId);
}
