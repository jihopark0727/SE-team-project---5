package com.example.demo.Service;

import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.SearchCondition;
import com.example.demo.Service.Interface.IPLIssueService;
import com.example.demo.Service.Interface.IUserIssueService;

import java.util.List;

public class PLIssueService implements IUserIssueService, IPLIssueService {
    @Override
    public ResponseDto<?> assignIssue(Long issueId, String assigneeId) {
        return null;
    }

    @Override
    public ResponseDto<?> updateStatus(Long issueId, String newStatus, String userID) {
        return null;
    }

    @Override
    public ResponseDto<List<Issue>> browseIssue(SearchCondition condition) {
        return null;
    }

    @Override
    public ResponseDto<?> updatePriority(Long issueId, String priority) {
        return null;
    }
}
