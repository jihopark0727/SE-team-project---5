package com.example.demo.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private Long issue_id;  // Issue 대신 issue_id로 수정
    private String content;
    private String user_id;
    private Date creation_time;

    public Long getIssueId() {
        return issue_id;
    }
}