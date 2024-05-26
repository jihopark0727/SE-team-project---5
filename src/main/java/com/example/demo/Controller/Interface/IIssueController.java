package com.example.demo.Controller.Interface;

import com.example.demo.DTO.IssueDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.SearchCondition;
import org.springframework.http.ResponseEntity;
import com.example.demo.Entity.Issue;

import java.util.List;
import java.util.Map;

public interface IIssueController {
    ResponseEntity<List<Issue>> browseIssues(Long projectId, String userType, SearchCondition con);
    ResponseEntity<Issue> getIssueById(Long projectId, Long issueId);
    ResponseEntity<Issue> addIssue(Long projectID, IssueDto issue);
    ResponseEntity<ResponseDto<?>> assignDevToIssue(Long projectId, Long issueId, Map<String, String> request);
    ResponseEntity<ResponseDto<?>> assignFixerToIssue(Long projectId, Long issueId, Map<String, String> request);
    ResponseEntity<ResponseDto<?>> updateIssueStatus(Long projectId, Long issueId, Map<String, String> request);
}
