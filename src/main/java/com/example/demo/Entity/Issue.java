package com.example.demo.Entity;

import com.example.demo.DTO.IssueDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    private Date reported_time;
    private Date last_modified_time;
    private String reporter_id;
    private String assignee_id;
    private String fixer_id;
    public Issue() {
    }
    public Issue(IssueDto dto){
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.status = dto.getStatus();
        this.priority = dto.getPriority();
        this.reported_time = dto.getReported_time();
        this.last_modified_time = dto.getLast_modified_time();
        this.reporter_id = dto.getReporter_id();
        this.assignee_id = dto.getAssignee_id();
        this.fixer_id = dto.getFixer_id();
        this.project = dto.getProject();
        this.id = dto.getId();
    }
}
