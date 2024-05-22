package com.example.demo.Service;

import com.example.demo.DTO.IssueDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Repository.IssueRepository;
import com.example.demo.Repository.ProjectRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public Issue addIssue(IssueDto issueDto, Long projectId, String reporterId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        User reporter = userRepository.findById(reporterId).orElse(null);
        if (project == null) {
            throw new IllegalArgumentException("Invalid project ID: " + projectId);
        }
        else if (reporter == null) {
            throw new IllegalArgumentException("Invalid reporter ID: " + reporterId);
        }
        Issue issue = new Issue(issueDto);
        issue.setProject(project);
        issue.setReporter(new User(reporterId));
        issue.setAssignee(null);
        issue.setFixer(null);
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
    public ResponseEntity<List<Issue>> searchByAssignee(String assigneeId) {
        List<Issue> issues = issueRepository.findByAssigneeId(assigneeId);
        if (issues != null && !issues.isEmpty()) {
            return ResponseEntity.ok(issues);  // 이슈 목록 반환
        } else {
            return ResponseEntity.noContent().build();  // 이슈가 없으면 204 No Content 반환
        }
    }
    public ResponseEntity<List<Issue>> searchByStatus(String status) {
        List<Issue> issues = issueRepository.findByStatus(status);
        if (issues != null && !issues.isEmpty()) {
            return ResponseEntity.ok(issues);  // 이슈 목록 반환
        } else {
            return ResponseEntity.noContent().build();  // 이슈가 없으면 204 No Content 반환
        }
    }
}
