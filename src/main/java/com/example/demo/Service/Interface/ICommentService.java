package com.example.demo.Service.Interface;

import com.example.demo.DTO.CommentDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Comment;

import java.util.List;

public interface ICommentService {
    ResponseDto<?> addComment(CommentDto commentDto);
    List<Comment> getCommentsByIssueId(Long issueId);
}
