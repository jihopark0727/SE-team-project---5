package com.example.demo.Service;

import com.example.demo.DTO.IssueDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.SearchCondition;
import com.example.demo.Service.Interface.ITesterIssueService;
import com.example.demo.Service.Interface.IUserIssueService;

import java.util.List;

public class TesterIssueService implements IUserIssueService, ITesterIssueService {

    @Override
    public ResponseDto<IssueDto> addIssue(IssueDto issue, Long projectId, String reporterId) {

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
