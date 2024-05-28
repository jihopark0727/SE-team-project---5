package com.example.demo.Controller.Interface;

import com.example.demo.DTO.AddUsersDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;

import java.util.List;

public interface IProjectController {
    List<Project> getAllProjects(String userId);
    Project getProjectById(Long project_id);
    List<User> getUsersByProjectId(Long projectId);
    Project addProject(Project project);
    ResponseDto<?> addUsersToProject(Long projectId, AddUsersDto requestBody);
}
