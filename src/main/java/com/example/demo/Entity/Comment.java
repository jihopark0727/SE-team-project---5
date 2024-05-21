package com.example.demo.Entity;

import jakarta.persistence.*;
import com.example.demo.DTO.CommentDto;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name="comment_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long comment_id;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "submit")
    private UserEntity submit;

    private Date creation_time;

    public Comment(CommentDto dto){
        this.comment = dto.getComment();
        this.issue = new Issue(dto.getIssue_id()); // Issue ID를 기반으로 Issue 객체 생성
        this.submit = dto.getSubmit();
        this.creation_time = dto.getCreation_time();
        this.comment_id = dto.getComment_id();
    }

    public Long getComment_id() { return comment_id; }
    public void setComment_id(Long comment_id) { this.comment_id = comment_id; }
    public Issue getIssue(){ return issue; }
    public void setIssue(Issue issue){ this.issue = issue; }
    public String getComment(){ return comment; }
    public void setComment(String comment){ this.comment = comment; }
    public UserEntity getSubmit(){ return submit; }
    public void setSubmit(UserEntity submit){ this.submit = submit; }
    public Date getCreation_time(){ return creation_time; }
    public void setCreation_time(Date creation_time){ this.creation_time = creation_time; }
}
