package com.example.demo.Service;

import com.example.demo.Entity.Issue;
import com.example.demo.Entity.Project;
import com.example.demo.Entity.UserEntity;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Repository.IssueRepository;
import com.example.demo.Repository.ProjectRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Issue> getIssuesByProjectId(Long projectId) {
        return issueRepository.findByProjectId(projectId);
    }

    public Optional<Issue> getIssueById(Long issueId) {
        return issueRepository.findById(issueId);
    }

    public Issue addIssue(Issue issue, Long projectId, String reporterId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        UserEntity reporter = userRepository.findById(reporterId).orElse(null);
        if (project == null) {
            throw new IllegalArgumentException("Invalid project ID: " + projectId);
        }
        else if (reporter == null) {
            throw new IllegalArgumentException("Invalid reporter ID: " + reporterId);
        }
        issue.setProject(project);
        issue.setReporter_id(reporterId);
        issue.setAssignee_id(null);
        issue.setFixer_id(null);
        issue.setStatus("new");
        issue.setReported_time(new Date());
        issue.setLast_modified_time(new Date());
        return issueRepository.save(issue);
    }
    @Transactional
    public ResponseDto<?> updateIssueState(Long issueId, String state) {
        Issue issue = issueRepository.findById(issueId).orElse(null);

        if(issue == null) {
            return ResponseDto.setFailed("Cannot find issue with id " + issueId);
        }
        else{
            issue.setStatus(state);
            return ResponseDto.setSuccess("success");
        }
    }
}
