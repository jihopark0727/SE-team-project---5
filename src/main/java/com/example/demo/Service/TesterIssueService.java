package com.example.demo.Service;

import com.example.demo.DTO.IssueDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.Project;
import com.example.demo.Entity.SearchCondition;
import com.example.demo.Entity.User;
import com.example.demo.Repository.IssueRepository;
import com.example.demo.Repository.ProjectRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.Interface.ITesterIssueService;
import com.example.demo.Service.Interface.IUserIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TesterIssueService implements IUserIssueService, ITesterIssueService {
    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public ResponseDto<Issue> addIssue(IssueDto issue, Long projectId, String reporterId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        User reporter = userRepository.findById(reporterId).orElse(null);
        if (project == null) {
            throw new IllegalArgumentException("Invalid project ID: " + projectId);
        }
        else if (reporter == null) {
            throw new IllegalArgumentException("Invalid reporter ID: " + reporterId);
        }
        issue.setProject(project);
        issue.setReporterId(reporterId);
        issue.setAssigneeId(null);
        issue.setFixerId(null);
        issue.setStatus("new");
        issue.setReported_time(new Date());
        issue.setLast_modified_time(new Date());
        Issue i = new Issue(issue);

        issueRepository.save(i);
        return ResponseDto.setSuccessData("Add issue success", i);
    }

    @Override
    public ResponseDto<?> updateStatus(Long issueId, String newStatus, String userId) {
        Optional<Issue> optionalIssue = issueRepository.findById(issueId);
        if (optionalIssue.isPresent()) {
            Issue issue = optionalIssue.get();

            // 추가된 로직: 상태 변경 가능 여부 검증
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
        } else {
            return ResponseDto.setFailed("Issue not found");
        }
    }

    @Override
    public ResponseDto<List<Issue>> browseIssue(String userId, SearchCondition condition) {
        String priority = condition.getPriority();
        String status = condition.getStatus();
        int select = 0;
        if(priority != null) select += 1;
        if(status != null) select += 2;
        switch(select){
            case 0:
                return ResponseDto.setSuccessData("list", issueRepository.findByReporterId(userId));
            case 1:
                return ResponseDto.setSuccessData("list", issueRepository.findByReporterIdAndPriority(userId, priority));
            case 2:
                return ResponseDto.setSuccessData("list", issueRepository.findByReporterIdAndStatus(userId, status));
            case 3:
                return ResponseDto.setSuccessData("list", issueRepository.findByReporterIdAndPriorityAndStatus(userId, priority, status));
        }
        return ResponseDto.setFailed("aa");
    }

    @Override
    public ResponseDto<?> updatePriority(Long issueId, String priority) {
        Issue issue = issueRepository.findById(issueId).orElse(null);

        if(issue == null) {
            return ResponseDto.setFailed("Cannot find issue with id " + issueId);
        } else {
            issue.setPriority(priority);
            issue.setLast_modified_time(new Date());
            issueRepository.save(issue);
            return ResponseDto.setSuccess("success");
        }
    }
}
