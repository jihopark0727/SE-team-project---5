package com.example.demo.Controller;

import com.example.demo.Controller.Interface.IIssueController;
import com.example.demo.DTO.IssueDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.SearchCondition;
import com.example.demo.Entity.User;
import com.example.demo.Service.Factory.IssueServiceFactory;
import com.example.demo.Service.Interface.ITesterIssueService;
import com.example.demo.Service.Interface.IUserIssueService;
import com.example.demo.Service.Interface.IUserService;
import com.example.demo.Service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects/{projectId}/issues")
public class IssueController implements IIssueController {

    @Autowired
    private ITesterIssueService testerService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private IssueServiceFactory factory;

    @Autowired
    private IUserService userService;

    @Override
    @PostMapping("/search")
    public ResponseEntity<List<Issue>> browseIssues(@PathVariable Long projectId,
                                                    @RequestParam String userId,
                                                    @RequestBody SearchCondition condition) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        String userType = user.getUser_type();
        IUserIssueService service = factory.getIssueService(userType);

        if (service == null) {
            return ResponseEntity.badRequest().build();
        }

        ResponseDto<List<Issue>> issues = issueService.browseIssues(projectId, condition);
        if (issues.isResult()) {
            List<Issue> issueList = issues.getData();
            if (issueList.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(issueList);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long projectId, @PathVariable Long issueId) {
        Optional<Issue> issue = issueService.getIssueById(issueId);
        return issue.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PostMapping
    public ResponseEntity<Issue> addIssue(@PathVariable Long projectId, @RequestBody IssueDto issue) {
        ResponseDto<Issue> response = testerService.addIssue(issue, projectId, issue.getReporterId());
        if (response.isResult()) {
            return ResponseEntity.ok(response.getData());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
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

    @Override
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

    @Override
    @PostMapping("/{issueId}/status")
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
