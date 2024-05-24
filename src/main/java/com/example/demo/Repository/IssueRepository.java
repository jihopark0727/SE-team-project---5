package com.example.demo.Repository;

import com.example.demo.Entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByProjectId(Long projectId);
    List<Issue> findByAssigneeId(String assigneeId);
    List<Issue> findByStatus(String status);
    List<Issue> findByProjectIdAndStatus(Long projectId, String status);

}
