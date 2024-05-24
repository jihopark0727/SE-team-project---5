package com.example.demo.DTO;

public class AssignIssueDto {
    private String assigneeId;
    private String plId;  // PL의 ID 필드 추가
    private String comment;

    // Getters and Setters
    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getPlId() {
        return plId;
    }

    public void setPlId(String plId) {
        this.plId = plId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
