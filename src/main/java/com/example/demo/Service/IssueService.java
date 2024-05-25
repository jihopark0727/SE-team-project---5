package com.example.demo.Service;

import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;
import com.example.demo.Repository.IssueRepository;
import com.example.demo.Repository.ProjectRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private UserService userService; // UserService 주입

    public List<Issue> getIssuesByProjectId(Long projectId) {
        return issueRepository.findByProjectId(projectId);
    }

    public Optional<Issue> getIssueById(Long issueId) {
        return issueRepository.findById(issueId);
    }

    public Issue addIssue(Issue issue, Long projectId, String reporterId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        User reporter = userRepository.findById(reporterId).orElse(null);
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

    public ResponseDto<?> assignDevToIssue(Long issueId, String assigneeId) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue == null) {
            return ResponseDto.setFailed("Issue not found");
        }

        issue.setAssignee_id(assigneeId);
        issue.setStatus("assigned");
        issue.setLast_modified_time(new Date());
        issueRepository.save(issue);
        return ResponseDto.setSuccess("Assignee updated successfully");
    }

    public ResponseDto<?> assignFixerToIssue(Long issueId, String fixerId) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue == null) {
            return ResponseDto.setFailed("Issue not found");
        }

        if (!fixerId.equals(issue.getAssignee_id())) {
            return ResponseDto.setFailed("Only the assigned dev can be the fixer");
        }

        issue.setFixer_id(fixerId);
        issue.setStatus("fixed");
        issue.setLast_modified_time(new Date());
        issueRepository.save(issue);
        return ResponseDto.setSuccess("Fixer assigned and issue fixed");
    }

    public ResponseDto<?> updateIssueStatus(Long issueId, String newStatus, String userId) {
        Optional<Issue> optionalIssue = issueRepository.findById(issueId);
        if (optionalIssue.isPresent()) {
            Issue issue = optionalIssue.get();

            // 추가된 로직: 상태 변경 가능 여부 검증
            if (newStatus.equals("resolved") && !userId.equals(issue.getReporter_id())) {
                return ResponseDto.setFailed("Only the reporter can change the status to resolved");
            }
            if (newStatus.equals("closed") && !userService.isUserPL(userId)) {
                return ResponseDto.setFailed("Only the project leader can change the status to closed");
            }

            issue.setStatus(newStatus);
            issue.setLast_modified_time(new Date());
            issueRepository.save(issue);
            return ResponseDto.setSuccess("Issue status updated successfully");
        } else {
            return ResponseDto.setFailed("Issue not found");
        }
    }
}
