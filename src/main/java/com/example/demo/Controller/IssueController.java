package com.example.demo.Controller;

import com.example.demo.DTO.AssignIssueDto;
import com.example.demo.DTO.IssueDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;
import com.example.demo.Service.IssueService;
import com.example.demo.Service.UserService;
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
    @Autowired
    private UserService userService;

    // dev 타입의 계정 조회
    @GetMapping("/dev-users")
    public ResponseEntity<List<User>> getDevUsers() {
        List<User> devUsers = userService.getUsersByType("dev");
        return ResponseEntity.ok(devUsers);
    }

    // 이슈의 assignee 지정 및 상태 변경
    @PostMapping("/{issueId}/assign")
    public ResponseEntity<?> assignIssue(
            @PathVariable Long projectId,
            @PathVariable Long issueId,
            @RequestBody AssignIssueDto assignIssueDto) {

        boolean result = issueService.assignIssue(issueId, assignIssueDto.getAssigneeId(), assignIssueDto.getPlId(), assignIssueDto.getComment());
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Issue assignment failed");
        }
    }


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

    // 프로젝트 ID에 따른 NEW 상태의 이슈 목록 가져오기
    @GetMapping("/status/new")
    public ResponseEntity<List<Issue>> getNewIssues(@PathVariable Long projectId) {
        List<Issue> newIssues = issueService.getIssuesByStatus(projectId, "new");
        if (newIssues != null && !newIssues.isEmpty()) {
            return ResponseEntity.ok(newIssues);  // 새로운 상태의 이슈 목록 반환
        } else {
            return ResponseEntity.noContent().build();  // 이슈가 없으면 204 No Content 반환
        }
    }


    @GetMapping("/{issue_id}")
    public Optional<Issue> getIssueById(@PathVariable Long issue_id) {
        return issueService.getIssueById(issue_id);
    }

    @PostMapping
    public ResponseEntity<Issue> addIssue(@PathVariable Long projectId, @RequestBody IssueDto issueDto) {
        System.out.println("Received reporter: " + issueDto.getReporter());  // 로거나 콘솔을 통해 리포터 객체 확인
        if (issueDto.getReporter() == null || issueDto.getReporter().getId() == null) {
            throw new IllegalArgumentException("Reporter ID is missing");
        }
        Issue newIssue = issueService.addIssue(issueDto, projectId, issueDto.getReporter().getId());
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
    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<List<Issue>> searchByAssinee(@PathVariable String assigneeId) {
        return issueService.searchByAssignee(assigneeId);
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Issue>> searchByStatus(@PathVariable String status){
        return issueService.searchByStatus(status);
    }
}
