package com.example.demo.Service;

import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;
import com.example.demo.Repository.ProjectRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.Interface.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import java.util.List;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository; // UserRepository 주입

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    @Override
    public Project addProject(Project project) {
        project.setCreation_time(new Date());
        project.setLast_modified_time(new Date());
        return projectRepository.save(project);
    }
    @Override
    @Transactional
    public void addUsersToProject(Long projectId, Set<String> userIds) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        Set<User> existingUsers = project.getUsers(); // 기존에 할당된 사용자들을 가져옵니다.

        // 새로 추가될 사용자들을 가져오고 Set으로 변환
        Set<User> newUsers = new HashSet<>(userRepository.findAllById(userIds));

        // 기존 사용자 세트에 새 사용자들을 추가
        if (existingUsers == null) {
            existingUsers = new HashSet<>(); // 만약 기존 사용자 세트가 null이면 새로운 HashSet을 생성
        }
        existingUsers.addAll(newUsers); // 새 사용자들을 추가

        project.setUsers(existingUsers); // 프로젝트에 업데이트된 사용자 세트 설정
        projectRepository.save(project); // 변경 사항을 저장
    }
    @Override
    public List<User> getUsersByProjectId(Long projectId) {
        return userRepository.findUsersByProjectId(projectId);
    }

}
