package com.example.demo.Controller;

import com.example.demo.Entity.Issue;
import com.example.demo.Service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @GetMapping("/project/{projectId}")
    public List<Issue> getIssuesByProjectId(@PathVariable Long projectId) {
        return issueService.getIssuesByProjectId(projectId);
    }

    @PostMapping
    public Issue addIssue(@RequestBody Issue issue) {
        return issueService.addIssue(issue);
    }
}
