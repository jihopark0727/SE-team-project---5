package com.example.demo.Service;

import com.example.demo.DTO.CommentDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Comment;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.Repository.IssueRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.Interface.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserRepository userRepository;  // 추가: UserRepository 주입

    @Override
    public ResponseDto<Issue> addComment(CommentDto commentDto) {
        // Issue ID를 기반으로 Issue 엔티티를 가져옴
        Issue issue = issueRepository.findById(commentDto.getIssueId()).orElse(null); // 메서드 이름 오타 수정
        if (issue == null) {
            return ResponseDto.setFailed("Invalid issue ID");
        }

        // User ID를 기반으로 UserEntity를 가져옴
        User user = userRepository.findById(commentDto.getUser_id()).orElse(null); // User 처리 추가
        if (user == null) {
            return ResponseDto.setFailed("Invalid user ID");
        }

        // Comment 엔티티 생성
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setIssue(issue);
        comment.setUser(user);
        comment.setCreation_time(new Date());

        // Comment 저장
        commentRepository.save(comment);

        return ResponseDto.setSuccessData("코멘트 생성에 성공했습니다.", issue);
    }
    @Override
    public List<Comment> getCommentsByIssueId(Long issueId) { // 파라미터 이름 변경
        return commentRepository.findByIssueId(issueId);
    }
}
