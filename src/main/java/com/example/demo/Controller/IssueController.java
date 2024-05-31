package com.example.demo.Controller;

import com.example.demo.Controller.Interface.IIssueController;
import com.example.demo.DTO.IssueDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.SearchCondition;
import com.example.demo.Service.Factory.IssueServiceFactory;
import com.example.demo.Service.Interface.IPLIssueService;
import com.example.demo.Service.Interface.ITesterIssueService;
import com.example.demo.Service.Interface.IUserIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects/{projectId}/issues")
public class IssueController implements IIssueController {
    @Autowired
    private ITesterIssueService testerService;
    @Autowired
    private IPLIssueService plService;
    @Autowired
    private IssueServiceFactory factory;

    // 프로젝트 ID에 따른 이슈 목록 가져오기
    @Override
    @PostMapping("/{userType}")
    public ResponseEntity<List<Issue>> browseIssues(@PathVariable Long projectId, @PathVariable String userType, @RequestBody SearchCondition con) {
        IUserIssueService service = factory.getIssueService(userType);
        if(service != null){
            ResponseDto<List<Issue>> issues = service.browseIssue(projectId, con);
            if(issues.isResult()){
                if(!issues.getData().isEmpty())
                    return ResponseEntity.ok(issues.getData());
                else
                    return ResponseEntity.noContent().build();
            }
            else{
                return ResponseEntity.badRequest().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    @Override
    @PostMapping
    public ResponseEntity<Issue> addIssue(@PathVariable Long projectId, @RequestBody IssueDto issue) {
        System.out.println("Received reporter ID: " + issue.getReporterId());  // 로거나 콘솔을 통해 리포터 ID 확인
        System.out.println("Received priority: " + issue.getPriority());
        ResponseDto<Issue> response = testerService.addIssue(issue, projectId, issue.getReporterId(), issue.getPriority());
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
        ResponseDto<?> response = plService.assignIssue(issueId, assigneeId);
        if (response.isResult()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }
    @Override
    @PutMapping("/{issueId}/status")
    public ResponseEntity<ResponseDto<?>> updateIssueStatus(@PathVariable Long projectId, @PathVariable Long issueId, @RequestBody Map<String, String> request) {
        String newStatus = request.get("status");
        String userId = request.get("userId");
        String userType = request.get("userType");
        IUserIssueService service = factory.getIssueService(userType);
        ResponseDto<?> response = service.updateStatus(issueId, newStatus, userId);
        if (response.isResult()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }
    @Override
    @PostMapping("/{issueId}/priority")
    public ResponseDto<?> updateIssuePriority(@PathVariable Long projectId, @PathVariable Long issueId, @RequestBody Map<String, String> request){
        String priority = request.get("priority");
        String userId = request.get("userId");
        return plService.updatePriority(userId, issueId, priority);
    }

    @Override
    @PostMapping("/{issueId}/issue_type")
    public ResponseDto<?> updateIssueType(@PathVariable Long projectId, @PathVariable Long issueId, @RequestBody Map<String, String> request){
        String issueType = request.get("issueType");
        String userId = request.get("userId");
        return plService.updateIssueType(userId, issueId, issueType);
    }
}
