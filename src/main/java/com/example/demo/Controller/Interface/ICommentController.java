package com.example.demo.Controller.Interface;

import com.example.demo.DTO.CommentDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Comment;
import com.example.demo.Entity.Issue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ICommentController {
    ResponseDto<Issue> addComment(Long projectId, Long issueId, CommentDto requestBody);
    List<Comment> getCommentsByIssueId(Long issueId);
}