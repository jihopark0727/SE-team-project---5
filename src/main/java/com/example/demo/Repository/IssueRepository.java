package com.example.demo.Repository;

import com.example.demo.Entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByProjectId(Long projectId);
    List<Issue> findByAssigneeId(String assigneeId);
    List<Issue> findByReporterId(String reporterId);
    List<Issue> findByPriorityAndStatus(String priority, String status);
    List<Issue> findByPriority(String priority);
    List<Issue> findByStatus(String status);
    List<Issue> findByAssigneeIdAndPriorityAndStatus(String assigneeId, String priority, String status);
    List<Issue> findByAssigneeIdAndPriority(String assigneeId, String priority);
    List<Issue> findByAssigneeIdAndStatus(String assigneeId, String status);
    List<Issue> findByReporterIdAndPriorityAndStatus(String reporterId, String priority, String status);
    List<Issue> findByReporterIdAndPriority(String reporterId, String priority);
    List<Issue> findByReporterIdAndStatus(String reporterId, String status);
    List<Issue> findByAssigneeIdAndReporterIdAndPriorityAndStatus(String assigneeId, String reporterId, String priority, String status);
    List<Issue> findByAssigneeIdAndReporterIdAndPriority(String assigneeId, String reporterId, String priority);
    List<Issue> findByAssigneeIdAndReporterIdAndStatus(String assigneeId, String reporterId, String status);

}
