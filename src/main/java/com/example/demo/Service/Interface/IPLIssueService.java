package com.example.demo.Service.Interface;
import com.example.demo.DTO.ResponseDto;

public interface IPLIssueService {
    ResponseDto<?> assignIssue(Long issueId, String assigneeId);
    ResponseDto<?> updatePriority(String userId, Long issueId, String priority);
}
