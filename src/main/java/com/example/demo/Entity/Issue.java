package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Data;
import com.example.demo.DTO.IssueDto;

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

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    private Date reported_time;
    private Date last_modified_time;
    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;
    @ManyToOne
    @JoinColumn(name = "assignee_id", nullable = true)
    private User assignee;
    @ManyToOne
    @JoinColumn(name = "fixer_id", nullable = true)
    private User fixer;
    public Issue() {
    }
    public Issue(Long id){
        this.id = id;
    }
    public Issue(IssueDto dto){
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.status = dto.getStatus();
        this.reported_time = dto.getReported_time();
        this.last_modified_time = dto.getLast_modified_time();
        this.reporter = dto.getReporter();
        this.assignee = dto.getAssignee();
        this.fixer = dto.getFixer();
        this.project = dto.getProject();
        this.id = dto.getId();
    }
}
