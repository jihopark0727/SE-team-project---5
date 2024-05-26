package com.example.demo.Service;

import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.SearchCondition;
import com.example.demo.Service.Interface.IUserIssueService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevIssueService implements IUserIssueService {

    @Override
    public ResponseDto<?> updateStatus(Long issueId, String newStatus, String userID) {
        return null;
    }

    @Override
    public ResponseDto<List<Issue>> browseIssue(String userType, SearchCondition condition) {
        return null;
    }

    @Override
    public ResponseDto<?> updatePriority(Long issueId, String priority) {
        return null;
    }
}
