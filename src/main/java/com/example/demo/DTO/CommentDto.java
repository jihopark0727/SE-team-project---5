package com.example.demo.DTO;
import com.example.demo.Entity.Issue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private Issue issue;  // Issue 대신 issue_id로 수정
    private String content;
    private String user_id;
    private Date creation_time;

    public Long getIssueId() {
        return issue.getId();
    }
}