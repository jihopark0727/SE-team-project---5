package com.example.demo.Service;

import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.SearchCondition;
import com.example.demo.Repository.IssueRepository;
import com.example.demo.Service.Interface.IPLIssueService;
import com.example.demo.Service.Interface.IUserIssueService;
import com.example.demo.Service.Interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PLIssueService extends UserIssueService implements IPLIssueService {

    @Override
    public ResponseDto<?> assignIssue(Long issueId, String assigneeId) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue == null) {
            return ResponseDto.setFailed("Issue not found");
        }
        String status = issue.getStatus();
        if (!status.equals("new") && !status.equals("reopened")) {
            return ResponseDto.setFailed("Only issues with status 'new' or 'reopened' can be assigned an assignee");
        }
        issue.setAssigneeId(assigneeId);
        issue.setStatus("assigned");
        issue.setLast_modified_time(new Date());
        issueRepository.save(issue);
        return ResponseDto.setSuccess("Assignee updated successfully");
    }

    @Override
    public ResponseDto<?> updateStatus(Long issueId, String newStatus, String userID) {
        Optional<Issue> optionalIssue = issueRepository.findById(issueId);
        if (optionalIssue.isPresent()) {
            Issue issue = optionalIssue.get();

            if (newStatus.equals("reopened")) {
                issue.setAssigneeId(null);
                issue.setFixerId(null);
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
