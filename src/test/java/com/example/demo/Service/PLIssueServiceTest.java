package com.example.demo.Service;

import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.User;
import com.example.demo.Repository.IssueRepository;
import com.example.demo.Repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PLIssueServiceTest {

    @InjectMocks
    private PLIssueService plIssueService;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignIssue_Success() {
        Long issueId = 1L;
        String assigneeId = "assignee1";

        Issue issue = new Issue();
        issue.setStatus("new");

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));

        ResponseDto<?> response = plIssueService.assignIssue(issueId, assigneeId);
        assertEquals("Assignee updated successfully", response.getMessage());
    }

    @Test
    public void testAssignIssue_IssueNotFound() {
        Long issueId = 1L;
        String assigneeId = "assignee1";

        when(issueRepository.findById(issueId)).thenReturn(Optional.empty());

        ResponseDto<?> response = plIssueService.assignIssue(issueId, assigneeId);
        assertEquals("Issue not found", response.getMessage());
    }

    @Test
    public void testAssignIssue_InvalidStatus() {
        Long issueId = 1L;
        String assigneeId = "assignee1";

        Issue issue = new Issue();
        issue.setStatus("closed");

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));

        ResponseDto<?> response = plIssueService.assignIssue(issueId, assigneeId);
        assertEquals("Only issues with status 'new' or 'reopened' can be assigned an assignee", response.getMessage());
    }

    @Test
    public void testUpdateStatus_Success() {
        Long issueId = 1L;
        String newStatus = "reopened";
        String userId = "user1";

        Issue issue = new Issue();

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));

        ResponseDto<?> response = plIssueService.updateStatus(issueId, newStatus, userId);
        assertEquals("Issue status updated successfully", response.getMessage());
    }

    @Test
    public void testUpdateStatus_IssueNotFound() {
        Long issueId = 1L;
        String newStatus = "reopened";
        String userId = "user1";

        when(issueRepository.findById(issueId)).thenReturn(Optional.empty());

        ResponseDto<?> response = plIssueService.updateStatus(issueId, newStatus, userId);
        assertEquals("Issue not found", response.getMessage());
    }

    @Test
    public void testUpdatePriority_Success() {
        String userId = "user1";
        Long issueId = 1L;
        String priority = "high";

        User user = new User();
        user.setUser_type("pl");

        Issue issue = new Issue();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));

        ResponseDto<?> response = plIssueService.updatePriority(userId, issueId, priority);
        assertEquals("success", response.getMessage());
    }

    @Test
    public void testUpdatePriority_UserNotPL() {
        String userId = "user1";
        Long issueId = 1L;
        String priority = "high";

        User user = new User();
        user.setUser_type("dev");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseDto<?> response = plIssueService.updatePriority(userId, issueId, priority);
        assertEquals("Priority can only changed by PL", response.getMessage());
    }

    @Test
    public void testUpdatePriority_UserNotFound() {
        String userId = "user1";
        Long issueId = 1L;
        String priority = "high";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseDto<?> response = plIssueService.updatePriority(userId, issueId, priority);
        assertEquals("Cannot find user", response.getMessage());
    }

    @Test
    public void testUpdatePriority_IssueNotFound() {
        String userId = "user1";
        Long issueId = 1L;
        String priority = "high";

        User user = new User();
        user.setUser_type("pl");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(issueRepository.findById(issueId)).thenReturn(Optional.empty());

        ResponseDto<?> response = plIssueService.updatePriority(userId, issueId, priority);
        assertEquals("Cannot find issue with id " + issueId, response.getMessage());
    }
}
