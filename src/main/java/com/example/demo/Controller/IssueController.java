package com.example.demo.Controller;

import com.example.demo.Entity.Issue;
import com.example.demo.Entity.Project;
import com.example.demo.Service.IssueService;
import com.example.demo.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Issue>> getIssuesByProjectId(@PathVariable Long projectId) {
        List<Issue> issues = issueService.getIssuesByProjectId(projectId);
        if (issues.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // If no issues are found, return HTTP 204 No Content
        }
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Issue> addIssue(@PathVariable Long projectId, @RequestBody Issue issue) {
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // If the project is not found, return HTTP 404 Not Found
        }
        issue.setProject(project); // Set the project of the issue to the retrieved project
        issue.setStatus("new"); // Initialize the status to 'new'
        issue.setReporterId("currentUserId"); // This needs to be dynamically set based on the current user session
        Issue createdIssue = issueService.addIssue(issue);
        return new ResponseEntity<>(createdIssue, HttpStatus.CREATED); // Return the created issue and HTTP 201 Created status
    }
}
