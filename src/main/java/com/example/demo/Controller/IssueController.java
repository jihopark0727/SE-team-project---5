package com.example.demo.Controller;

import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long projectId, @PathVariable Long issueId) {
        Optional<Issue> issue = issueService.getIssueById(issueId);
        if (issue.isPresent()) {
            return ResponseEntity.ok(issue.get());
        } else {
            return ResponseEntity.notFound().build();
        }
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

    @PostMapping("/{issueId}/assign")
    public ResponseEntity<ResponseDto<?>> assignDevToIssue(@PathVariable Long projectId, @PathVariable Long issueId, @RequestBody Map<String, String> request) {
        String assigneeId = request.get("assigneeId");
        ResponseDto<?> response = issueService.assignDevToIssue(issueId, assigneeId);
        if (response.isResult()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }

    @PostMapping("/{issueId}/fix")
    public ResponseEntity<ResponseDto<?>> assignFixerToIssue(@PathVariable Long projectId, @PathVariable Long issueId, @RequestBody Map<String, String> request) {
        String fixerId = request.get("fixerId");
        ResponseDto<?> response = issueService.assignFixerToIssue(issueId, fixerId);
        if (response.isResult()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }

    @PutMapping("/{issueId}/status")
    public ResponseEntity<ResponseDto<?>> updateIssueStatus(@PathVariable Long projectId, @PathVariable Long issueId, @RequestBody Map<String, String> request) {
        String newStatus = request.get("status");
        String userId = request.get("userId");
        ResponseDto<?> response = issueService.updateIssueStatus(issueId, newStatus, userId);
        if (response.isResult()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }
}
