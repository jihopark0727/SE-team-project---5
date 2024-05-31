package com.example.demo.Repository;

import com.example.demo.Entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
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

    List<Issue> findByProjectIdAndIssueType(Long projectId, String issueType);

    List<Issue> findByProjectIdAndPriorityAndIssueType(Long projectId, String priority, String issueType);

    List<Issue> findByProjectIdAndStatusAndIssueType(Long projectId, String status, String issueType);

    List<Issue> findByProjectIdAndPriorityAndStatusAndIssueType(Long projectId, String priority, String status, String issueType);

    List<Issue> findByProjectIdAndReporterIdAndIssueType(Long projectId, String reporterId, String issueType);

    List<Issue> findByProjectIdAndReporterIdAndPriorityAndIssueType(Long projectId, String reporterId, String priority, String issueType);

    List<Issue> findByProjectIdAndReporterIdAndStatusAndIssueType(Long projectId, String reporterId, String status, String issueType);

    List<Issue> findByProjectIdAndReporterIdAndPriorityAndStatusAndIssueType(Long projectId, String reporterId, String priority, String status, String issueType);

    List<Issue> findByProjectIdAndAssigneeIdAndIssueType(Long projectId, String assigneeId, String issueType);

    List<Issue> findByProjectIdAndAssigneeIdAndPriorityAndIssueType(Long projectId, String assigneeId, String priority, String issueType);

    List<Issue> findByProjectIdAndAssigneeIdAndStatusAndIssueType(Long projectId, String assigneeId, String status, String issueType);

    List<Issue> findByProjectIdAndAssigneeIdAndPriorityAndStatusAndIssueType(Long projectId, String assigneeId, String priority, String status, String issueType);

    List<Issue> findByProjectIdAndAssigneeIdAndReporterIdAndIssueType(Long projectId, String assigneeId, String reporterId, String issueType);

    List<Issue> findByProjectIdAndAssigneeIdAndReporterIdAndPriorityAndIssueType(Long projectId, String assigneeId, String reporterId, String priority, String issueType);

    List<Issue> findByProjectIdAndAssigneeIdAndReporterIdAndStatusAndIssueType(Long projectId, String assigneeId, String reporterId, String status, String issueType);

    List<Issue> findByProjectIdAndAssigneeIdAndReporterIdAndPriorityAndStatusAndIssueType(Long projectId, String assigneeId, String reporterId, String priority, String status, String issueType);

    Long countByIssueTypeAndFixerId(String issueType, String fixerId);
}
