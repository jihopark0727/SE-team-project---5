package com.example.demo.Service;

import com.example.demo.Entity.Project;
import com.example.demo.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    public Project addProject(Project project) {
        project.setCreation_time(new Date());
        project.setLast_modified_time(new Date());
        return projectRepository.save(project);
    }
}
