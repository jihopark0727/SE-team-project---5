package com.example.demo.Controller.Interface;

import com.example.demo.DTO.ResponseDto;
import org.springframework.http.ResponseEntity;
import com.example.demo.Entity.Issue;

import java.util.List;
import java.util.Map;

public interface IIssueController {
    ResponseEntity<List<Issue>> getIssuesByProjectId(Long projectId);
    ResponseEntity<Issue> getIssueById(Long projectId, Long issueId);
    ResponseEntity<Issue> addIssue(Long projectID, Issue issue);
    ResponseEntity<ResponseDto<?>> assignDevToIssue(Long projectId, Long issueId, Map<String, String> request);
    ResponseEntity<ResponseDto<?>> assignFixerToIssue(Long projectId, Long issueId, Map<String, String> request);
    ResponseEntity<ResponseDto<?>> updateIssueStatus(Long projectId, Long issueId, Map<String, String> request);
}
