package com.example.demo.Service;

import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.Project;
import com.example.demo.Entity.QIssue;
import com.example.demo.Entity.SearchCondition;
import com.example.demo.Entity.User;
import com.example.demo.Repository.IssueRepository;
import com.example.demo.Repository.ProjectRepository;
import com.example.demo.Repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
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
    private UserService userService;

    public List<Issue> getIssuesByProjectId(Long projectId) {
        return issueRepository.findByProjectId(projectId);
    }

    public Optional<Issue> getIssueById(Long issueId) {
        return issueRepository.findById(issueId);
    }

    public Issue addIssue(Issue issue, Long projectId, String reporterId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + projectId));
        User reporter = userRepository.findById(reporterId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reporter ID: " + reporterId));

        issue.setProject(project);
        issue.setReporterId(reporterId);
        issue.setAssigneeId(null);
        issue.setFixerId(null);
        issue.setStatus("new");
        issue.setReported_time(new Date());
        issue.setLast_modified_time(new Date());
        return issueRepository.save(issue);
    }

    public ResponseDto<?> assignDevToIssue(Long issueId, String assigneeId) {
        Issue issue = issueRepository.findById(issueId)
                .orElse(null);
        if (issue == null) {
            return ResponseDto.setFailed("Issue not found");
        }

        issue.setAssigneeId(assigneeId);
        issue.setStatus("assigned");
        issue.setLast_modified_time(new Date());
        issueRepository.save(issue);
        return ResponseDto.setSuccess("Assignee updated successfully");
    }

    public ResponseDto<?> assignFixerToIssue(Long issueId, String fixerId) {
        Issue issue = issueRepository.findById(issueId)
                .orElse(null);
        if (issue == null) {
            return ResponseDto.setFailed("Issue not found");
        }

        if (!fixerId.equals(issue.getAssigneeId())) {
            return ResponseDto.setFailed("Only the assigned dev can be the fixer");
        }

        issue.setFixerId(fixerId);
        issue.setStatus("fixed");
        issue.setLast_modified_time(new Date());
        issueRepository.save(issue);
        return ResponseDto.setSuccess("Fixer assigned and issue fixed");
    }

    public ResponseDto<?> updateIssueStatus(Long issueId, String newStatus, String userId) {
        return issueRepository.findById(issueId)
                .map(issue -> {
                    if (newStatus.equals("resolved") && !userId.equals(issue.getReporterId())) {
                        return ResponseDto.setFailed("Only the reporter can change the status to resolved");
                    }
                    if (newStatus.equals("closed") && !userService.isUserPL(userId)) {
                        return ResponseDto.setFailed("Only the project leader can change the status to closed");
                    }

                    issue.setStatus(newStatus);
                    issue.setLast_modified_time(new Date());
                    issueRepository.save(issue);
                    return ResponseDto.setSuccess("Issue status updated successfully");
                })
                .orElse(ResponseDto.setFailed("Issue not found"));
    }

    public ResponseDto<List<Issue>> browseIssues(Long projectId, SearchCondition condition) {
        try {
            QIssue qIssue = QIssue.issue;
            BooleanBuilder builder = new BooleanBuilder();

            builder.and(qIssue.project.id.eq(projectId));

            if (condition.getAssignee() != null && !condition.getAssignee().isEmpty()) {
                builder.and(qIssue.assigneeId.eq(condition.getAssignee()));
            }
            if (condition.getReporter() != null && !condition.getReporter().isEmpty()) {
                builder.and(qIssue.reporterId.eq(condition.getReporter()));
            }
            if (condition.getPriority() != null && !condition.getPriority().isEmpty()) {
                builder.and(qIssue.priority.eq(condition.getPriority()));
            }
            if (condition.getStatus() != null && !condition.getStatus().isEmpty()) {
                builder.and(qIssue.status.eq(condition.getStatus()));
            }

            List<Issue> issues = (List<Issue>) issueRepository.findAll(builder);
            return ResponseDto.setSuccessData("Issue list fetched successfully", issues);
        } catch (Exception e) {
            return ResponseDto.setFailed("Failed to fetch issues");
        }
    }
}
