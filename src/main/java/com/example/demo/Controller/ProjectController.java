package com.example.demo.Controller;

import com.example.demo.DTO.AddUsersDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;
import com.example.demo.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{project_id}")
    public Project getProjectById(@PathVariable Long project_id) {
        return projectService.getProjectById(project_id);
    }

    @GetMapping("/{projectId}/users")
    public List<User> getUsersByProjectId(@PathVariable Long projectId) {
        return projectService.getUsersByProjectId(projectId);
    }

    @PostMapping
    public Project addProject(@RequestBody Project project) {
        return projectService.addProject(project);
    }

    @PostMapping("/{projectId}/addUsers")
    public ResponseDto<?> addUsersToProject(@PathVariable Long projectId, @RequestBody AddUsersDto requestBody) {
        projectService.addUsersToProject(projectId, requestBody.getUserIds());
        return ResponseDto.setSuccess("Users have been successfully added.");
    }

}
