package com.example.demo.Service.Interface;

import com.example.demo.DTO.CommentDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Comment;
import com.example.demo.Entity.Issue;

import java.util.List;

public interface ICommentService {
    ResponseDto<Issue> addComment(CommentDto commentDto);
    List<Comment> getCommentsByIssueId(Long issueId);
}
