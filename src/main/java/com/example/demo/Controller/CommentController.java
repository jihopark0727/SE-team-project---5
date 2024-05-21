package com.example.demo.Controller;

import com.example.demo.Entity.Comment;
import com.example.demo.DTO.CommentDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/issues/{issueId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseDto<?> addComment(@PathVariable Long projectId, @PathVariable Long issueId, @RequestBody CommentDto requestBody) {
        // 추가: Issue ID를 CommentDto에 설정
        requestBody.setIssue_id(issueId);
        return commentService.addComment(requestBody);
    }

    @GetMapping
    public List<Comment> getCommentsByIssueId(@PathVariable Long issueId) {
        return commentService.getCommentsByIssueId(issueId);
    }
}
