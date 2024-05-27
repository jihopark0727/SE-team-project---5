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


    @Override
    public ResponseDto<?> updateStatus(Long issueId, String newStatus, String userId) {
        Optional<Issue> optionalIssue = issueRepository.findById(issueId);
        if (optionalIssue.isPresent()) {
            Issue issue = optionalIssue.get();

            if (!userId.equals(issue.getAssigneeId())) { // 해당 issue의 assignee만 fixed로 고칠 수 있음.
                return ResponseDto.setFailed("Only the assignee can change the status to fixed");
            }
            else{
                if(newStatus.equals("fixed")){
                    issue.setFixerId(userId);
                }
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
    public ResponseDto<List<Issue>> browseIssue(Long projectId, SearchCondition condition) {
        String reporterId = condition.getReporter();
        String assigneeId = condition.getAssignee();
        String priority = condition.getPriority();
        String status = condition.getStatus();
        int select = 0;
        if(priority != null) select += 1;
        if(status != null) select += 2;
        if(reporterId != null) select += 4;
        if(assigneeId != null) select += 8;
        switch(select){
            case 0:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectId(projectId));
            case 1:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndPriority(projectId, priority));
            case 2:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndStatus(projectId, status));
            case 3:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndPriorityAndStatus(projectId, priority, status));
            case 4:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndReporterId(projectId, reporterId));
            case 5:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndReporterIdAndPriority(projectId, reporterId, priority));
            case 6:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndReporterIdAndStatus(projectId, reporterId, status));
            case 7:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndReporterIdAndPriorityAndStatus(projectId, reporterId, priority, status));
            case 8:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndAssigneeId(projectId, assigneeId));
            case 9:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndAssigneeIdAndPriority(projectId, assigneeId, priority));
            case 10:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndAssigneeIdAndStatus(projectId, assigneeId, status));
            case 11:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndAssigneeIdAndPriorityAndStatus(projectId, assigneeId, priority, status));
            case 12:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndAssigneeIdAndReporterId(projectId, assigneeId, reporterId));
            case 13:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndAssigneeIdAndReporterIdAndPriority(projectId, assigneeId, reporterId, priority));
            case 14:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndAssigneeIdAndReporterIdAndStatus(projectId, assigneeId, reporterId, status));
            case 15:
                return ResponseDto.setSuccessData("list", issueRepository.findByProjectIdAndAssigneeIdAndReporterIdAndPriorityAndStatus(projectId, assigneeId, reporterId, priority, status));
        }
        return ResponseDto.setFailed("failed");
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
