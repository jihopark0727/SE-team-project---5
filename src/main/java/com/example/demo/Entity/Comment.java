package com.example.demo.Entity;

import jakarta.persistence.*;
import com.example.demo.DTO.CommentDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "creation_time")
    private Date creation_time;

    // DTO를 기반으로 Comment 객체를 생성하는 생성자
    public Comment(CommentDto dto) {
        this.id = dto.getId();
        this.content = dto.getContent();
        // 이 부분은 실제 구현에서는 레포지토리를 통해 엔티티를 로드해야 합니다.
        this.issue = dto.getIssue();
        this.user = new User();  // 임시 처리
        this.user.setId(dto.getUser_id());
        this.creation_time = dto.getCreation_time();
    }

}
