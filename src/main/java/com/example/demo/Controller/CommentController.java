package com.example.demo.Controller;
import com.example.demo.Entity.Comment;
import com.example.demo.DTO.CommentDto;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projects/{project_id}/{issue_id}/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/add_comment")
    public ResponseDto<?> addComment(@RequestBody CommentDto requestBody) {
        return commentService.addComment(requestBody);
    }
    @GetMapping("/issue/{issueId}")
    public List<Comment> getCommentsByIssueId(@PathVariable Long issueId) {
        return commentRepository.findByIssueId(issueId);
    }
}
