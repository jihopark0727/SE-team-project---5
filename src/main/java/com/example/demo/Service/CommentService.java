package com.example.demo.Service;

import com.example.demo.DTO.CommentDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Comment;
import com.example.demo.Entity.Issue;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.Repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private IssueRepository issueRepository;

    public ResponseDto<?> addComment(CommentDto commentDto) {
        // Issue ID를 기반으로 Issue 엔티티를 가져옴
        Issue issue = issueRepository.findById(commentDto.getIssue_id()).orElse(null);
        if (issue == null) {
            return ResponseDto.setFailed("Invalid issue ID");
        }

        // Comment 엔티티 생성
        Comment comment = new Comment();
        comment.setComment(commentDto.getComment());
        comment.setIssue(issue);
        comment.setSubmit(commentDto.getSubmit());
        comment.setCreation_time(new Date());

        // Comment 저장
        commentRepository.save(comment);

        return ResponseDto.setSuccess("코멘트 생성에 성공했습니다.");
    }

    public List<Comment> getCommentsByIssueId(Long issue_id) {
        return commentRepository.findByIssueId(issue_id);
    }
}
