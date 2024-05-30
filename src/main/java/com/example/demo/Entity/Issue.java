package com.example.demo.Entity;

import com.example.demo.DTO.IssueDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;
    private String priority;
    private String issueType;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    private Date reported_time;
    private Date last_modified_time;
    private String reporterId;
    private String assigneeId;
    private String fixerId;
    public Issue() {
    }
    public Issue(IssueDto dto){
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.status = dto.getStatus();
        this.priority = dto.getPriority();
        this.issueType = dto.getIssueType();
        this.reported_time = dto.getReported_time();
        this.last_modified_time = dto.getLast_modified_time();
        this.reporterId = dto.getReporterId();
        this.assigneeId = dto.getAssigneeId();
        this.fixerId = dto.getFixerId();
        this.project = dto.getProject();
        this.id = dto.getId();
    }
}