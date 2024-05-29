package com.example.demo.Repository;

import com.example.demo.Entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long>, QuerydslPredicateExecutor<Issue> {
    List<Issue> findByProjectId(Long projectId);

    List<Issue> findByProjectIdAndAssigneeId(Long projectId, String assigneeId);

    List<Issue> findByProjectIdAndReporterId(Long projectId, String reporterId);

    List<Issue> findByProjectIdAndPriorityAndStatus(Long projectId, String priority, String status);

    List<Issue> findByProjectIdAndPriority(Long projectId, String priority);

    List<Issue> findByProjectIdAndStatus(Long projectId, String status);

    List<Issue> findByProjectIdAndAssigneeIdAndPriorityAndStatus(Long projectId, String assigneeId, String priority, String status);

    List<Issue> findByProjectIdAndAssigneeIdAndPriority(Long projectId, String assigneeId, String priority);

    List<Issue> findByProjectIdAndAssigneeIdAndStatus(Long projectId, String assigneeId, String status);

    List<Issue> findByProjectIdAndReporterIdAndPriorityAndStatus(Long projectId, String reporterId, String priority, String status);

    List<Issue> findByProjectIdAndReporterIdAndPriority(Long projectId, String reporterId, String priority);

    List<Issue> findByProjectIdAndReporterIdAndStatus(Long projectId, String reporterId, String status);

    List<Issue> findByProjectIdAndAssigneeIdAndReporterIdAndPriorityAndStatus(Long projectId, String assigneeId, String reporterId, String priority, String status);

    List<Issue> findByProjectIdAndAssigneeIdAndReporterIdAndPriority(Long projectId, String assigneeId, String reporterId, String priority);

    List<Issue> findByProjectIdAndAssigneeIdAndReporterIdAndStatus(Long projectId, String assigneeId, String reporterId, String status);

    List<Issue> findByProjectIdAndAssigneeIdAndReporterId(Long projectId, String assigneeId, String reporterId);
}
