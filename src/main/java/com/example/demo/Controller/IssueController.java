package com.example.demo.Controller;

import com.example.demo.Entity.Issue;
import com.example.demo.Entity.Project;
import com.example.demo.Service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.DTO.ResponseDto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects/{projectId}/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    // 프로젝트 ID에 따른 이슈 목록 가져오기
    @GetMapping
    public ResponseEntity<List<Issue>> getIssuesByProjectId(@PathVariable Long projectId) {
        List<Issue> issues = issueService.getIssuesByProjectId(projectId);
        if (issues != null && !issues.isEmpty()) {
            return ResponseEntity.ok(issues);  // 이슈 목록 반환
        } else {
            return ResponseEntity.noContent().build();  // 이슈가 없으면 204 No Content 반환
        }
    }

    @GetMapping("/{issue_id}")
    public Optional<Issue> getIssueById(@PathVariable Long issue_id) {
        return issueService.getIssueById(issue_id);
    }

    @PostMapping
    public ResponseEntity<Issue> addIssue(@PathVariable Long projectId, @RequestBody Issue issue) {
        System.out.println("Received reporter ID: " + issue.getReporter_id());  // 로거나 콘솔을 통해 리포터 ID 확인
        Issue newIssue = issueService.addIssue(issue, projectId, issue.getReporter_id());
        if (newIssue != null) {
            return ResponseEntity.ok(newIssue);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/{issueId}/updateIssueState")
    public ResponseDto<?> updateIssueState(@PathVariable Long issueId, @RequestBody String state){
        return issueService.updateIssueState(issueId, state);
    }

}
