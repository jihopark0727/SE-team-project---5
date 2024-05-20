package com.example.demo.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Issue")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 10)
    private String status;

    @Column(name = "reported_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reported_time;

    @Column(name = "last_modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date last_modified_time;

    @Column(name = "reporter_id")
    private String reporter_id;

    @Column(name = "assignee_id")
    private String assignee_id;

    @Column(name = "fixer_id")
    private String fixer_id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReporter_id() {
        return reporter_id;
    }

    public void setReporter_id(String reporter_id) {
        this.reporter_id = reporter_id;
    }

    public String getAssignee_id() {
        return assignee_id;
    }

    public void setAssignee_id(String assignee_id) {
        this.assignee_id = assignee_id;
    }

    public String getFixer_id() {
        return fixer_id;
    }

    public void setFixer_id(String fixer_id) {
        this.fixer_id = fixer_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getReported_time() {
        return reported_time;
    }

    public void setReported_time(Date reported_time) {
        this.reported_time = reported_time;
    }

    public Date getLast_modified_time() {
        return last_modified_time;
    }

    public void setLast_modified_time(Date last_modified_time) {
        this.last_modified_time = last_modified_time;
    }
}
