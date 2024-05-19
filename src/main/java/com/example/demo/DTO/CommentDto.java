package com.example.demo.DTO;
import com.example.demo.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long comment_id;
    private Issue issue;
    private String comment;
    private UserEntity submit;
    private Date creation_time;
}