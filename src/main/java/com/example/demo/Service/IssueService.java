package com.example.demo.Service;

import com.example.demo.Entity.Issue;
import com.example.demo.Repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public List<Issue> getIssuesByProjectId(Long projectId) {
        return issueRepository.findByProjectId(projectId);
    }

    public Issue addIssue(Issue issue) {
        issue.setStatus("new");
        issue.setReportedTime(new Date());
        issue.setLastModifiedTime(new Date());
        return issueRepository.save(issue);
    }
}
