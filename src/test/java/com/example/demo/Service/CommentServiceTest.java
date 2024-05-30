package com.example.demo.Service;

import com.example.demo.DTO.CommentDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Comment;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.Repository.IssueRepository;
import com.example.demo.Repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddComment_Success() {
        Long issueId = 1L;
        String userId = "user1";
        CommentDto commentDto = new CommentDto();
        commentDto.setIssue_id(issueId);
        commentDto.setUser_id(userId);
        commentDto.setContent("This is a test comment");

        Issue issue = new Issue();
        User user = new User();

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseDto<Issue> response = commentService.addComment(commentDto);

        assertNotNull(response);
        assertEquals("코멘트 생성에 성공했습니다.", response.getMessage());
        verify(issueRepository, times(1)).findById(issueId);
        verify(userRepository, times(1)).findById(userId);
        verify(commentRepository, times(1)).save(any(Comment.class));
        verify(issueRepository, times(1)).save(issue);
    }

    @Test
    public void testAddComment_Failed_InvalidIssueId() {
        Long issueId = 1L;
        String userId = "user1";
        CommentDto commentDto = new CommentDto();
        commentDto.setIssue_id(issueId);
        commentDto.setUser_id(userId);
        commentDto.setContent("This is a test comment");

        when(issueRepository.findById(issueId)).thenReturn(Optional.empty());

        ResponseDto<Issue> response = commentService.addComment(commentDto);

        assertNotNull(response);
        assertEquals("Invalid issue ID", response.getMessage());
        verify(issueRepository, times(1)).findById(issueId);
        verify(userRepository, times(0)).findById(userId);
        verify(commentRepository, times(0)).save(any(Comment.class));
        verify(issueRepository, times(0)).save(any(Issue.class));
    }

    @Test
    public void testAddComment_Failed_InvalidUserId() {
        Long issueId = 1L;
        String userId = "user1";
        CommentDto commentDto = new CommentDto();
        commentDto.setIssue_id(issueId);
        commentDto.setUser_id(userId);
        commentDto.setContent("This is a test comment");

        Issue issue = new Issue();

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseDto<Issue> response = commentService.addComment(commentDto);

        assertNotNull(response);
        assertEquals("Invalid user ID", response.getMessage());
        verify(issueRepository, times(1)).findById(issueId);
        verify(userRepository, times(1)).findById(userId);
        verify(commentRepository, times(0)).save(any(Comment.class));
        verify(issueRepository, times(0)).save(issue);
    }

    @Test
    public void testGetCommentsByIssueId() {
        Long issueId = 1L;
        List<Comment> comments = Arrays.asList(new Comment(), new Comment());

        when(commentRepository.findByIssueId(issueId)).thenReturn(comments);

        List<Comment> result = commentService.getCommentsByIssueId(issueId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(commentRepository, times(1)).findByIssueId(issueId);
    }
}
