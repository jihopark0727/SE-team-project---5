package com.example.demo.Service.Interface;

import com.example.demo.DTO.IssueDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;

public interface ITesterIssueService {
    ResponseDto<Issue> addIssue(IssueDto issue, Long projectId, String reporterId, String priority, String issueType);
}
