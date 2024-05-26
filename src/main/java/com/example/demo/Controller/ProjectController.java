package com.example.demo.Controller;

import com.example.demo.Controller.Interface.IProjectController;
import com.example.demo.DTO.AddUsersDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;
import com.example.demo.Service.Interface.IProjectService;
import com.example.demo.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/projects")
public class ProjectController implements IProjectController {

    @Autowired
    private IProjectService projectService;

    @Override
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @Override
    @GetMapping("/{project_id}")
    public Project getProjectById(@PathVariable Long project_id) {
        return projectService.getProjectById(project_id);
    }

    @Override
    @GetMapping("/{projectId}/users")
    public List<User> getUsersByProjectId(@PathVariable Long projectId) {
        return projectService.getUsersByProjectId(projectId);
    }

    @Override
    @PostMapping
    public Project addProject(@RequestBody Project project) {
        return projectService.addProject(project);
    }

    @Override
    @PostMapping("/{projectId}/addUsers")
    public ResponseDto<?> addUsersToProject(@PathVariable Long projectId, @RequestBody AddUsersDto requestBody) {
        projectService.addUsersToProject(projectId, requestBody.getUserIds());
        return ResponseDto.setSuccess("Users have been successfully added.");
    }

}
