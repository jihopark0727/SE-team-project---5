package com.example.demo.DTO;
import com.example.demo.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long comment_id;
    private Long issue_id;  // Issue 대신 issue_id로 수정
    private String comment;
    private UserEntity submit;
    private Date creation_time;
}
