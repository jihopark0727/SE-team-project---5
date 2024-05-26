package com.example.demo.Service;

import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.SearchCondition;
import com.example.demo.Repository.IssueRepository;
import com.example.demo.Repository.ProjectRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.Interface.IUserIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DevIssueService implements IUserIssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public ResponseDto<?> updateStatus(Long issueId, String newStatus, String userId) {
        Optional<Issue> optionalIssue = issueRepository.findById(issueId);
        if (optionalIssue.isPresent()) {
            Issue issue = optionalIssue.get();

            if (!userId.equals(issue.getAssigneeId())) { // 해당 issue의 assignee만 fixed로 고칠 수 있음.
                return ResponseDto.setFailed("Only the assignee can change the status to fixed");
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
    public ResponseDto<List<Issue>> browseIssue(Long projectId, String userId, SearchCondition condition) {
        String priority = condition.getPriority();
        String status = condition.getStatus();
        int select = 0;
        if(priority != null) select += 1;
        if(status != null) select += 2;
        switch(select){
            case 0:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndAssigneeId(projectId, userId));
            case 1:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndAssigneeIdAndPriority(projectId, userId, priority));
            case 2:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndAssigneeIdAndStatus(projectId, userId, status));
            case 3:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndAssigneeIdAndReporterIdAndPriorityAndStatus(projectId, userId, condition.getSubmit(), priority, status));
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
