package com.example.demo.Service.Interface;

import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.SearchCondition;

import java.util.List;

public interface IUserIssueService {
    ResponseDto<?> updateStatus(Long issueId, String newStatus, String userID);
    ResponseDto<List<Issue>> browseIssue(SearchCondition condition);
    ResponseDto<?> updatePriority(Long issueId, String priority);
}
