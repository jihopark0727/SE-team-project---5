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


import java.util.Date;
import java.util.Optional;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PLIssueServiceTest {


    @Mock
    private UserRepository userRepository;


    @Mock
    private IssueRepository issueRepository;


    @InjectMocks
    private PLIssueService plIssueService;


    private Issue issue;
    private User user;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);


        user = new User();
        user.setId("testUser");
        user.setUser_type("pl");


        issue = new Issue();
        issue.setId(1L);
        issue.setStatus("new");
        issue.setLast_modified_time(new Date());
    }


    @Test
    public void testAssignIssueSuccess() {
        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));


        ResponseDto<?> response = plIssueService.assignIssue(1L, "assigneeUser");


        assertEquals("Assignee updated successfully", response.getMessage());
        verify(issueRepository, times(1)).save(issue);
    }


    @Test
    public void testAssignIssueNotFound() {
        when(issueRepository.findById(1L)).thenReturn(Optional.empty());


        ResponseDto<?> response = plIssueService.assignIssue(1L, "assigneeUser");


        assertEquals("Issue not found", response.getMessage());
        verify(issueRepository, never()).save(any(Issue.class));
    }


    @Test
    public void testAssignIssueInvalidStatus() {
        issue.setStatus("closed");
        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));


        ResponseDto<?> response = plIssueService.assignIssue(1L, "assigneeUser");


        assertEquals("Only issues with status 'new' or 'reopened' can be assigned an assignee", response.getMessage());
        verify(issueRepository, never()).save(any(Issue.class));
    }


    @Test
    public void testUpdateStatusSuccess() {
        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));


        ResponseDto<?> response = plIssueService.updateStatus(1L, "reopened", "testUser");


        assertEquals("Issue status updated successfully", response.getMessage());
        verify(issueRepository, times(1)).save(issue);
    }


    @Test
    public void testUpdateStatusIssueNotFound() {
        when(issueRepository.findById(1L)).thenReturn(Optional.empty());


        ResponseDto<?> response = plIssueService.updateStatus(1L, "reopened", "testUser");


        assertEquals("Issue not found", response.getMessage());
        verify(issueRepository, never()).save(any(Issue.class));
    }


    @Test
    public void testUpdatePrioritySuccess() {
        when(userRepository.findById("testUser")).thenReturn(Optional.of(user));
        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));


        ResponseDto<?> response = plIssueService.updatePriority("testUser", 1L, "high");


        assertEquals("success", response.getMessage());
        verify(issueRepository, times(1)).save(issue);
    }


    @Test
    public void testUpdatePriorityUserNotPL() {
        user.setUser_type("dev");
        when(userRepository.findById("testUser")).thenReturn(Optional.of(user));


        ResponseDto<?> response = plIssueService.updatePriority("testUser", 1L, "high");


        assertEquals("Priority can only be changed by PL", response.getMessage());
        verify(issueRepository, never()).save(any(Issue.class));
    }


    @Test
    public void testUpdatePriorityIssueNotFound() {
        when(userRepository.findById("testUser")).thenReturn(Optional.of(user));
        when(issueRepository.findById(1L)).thenReturn(Optional.empty());


        ResponseDto<?> response = plIssueService.updatePriority("testUser", 1L, "high");


        assertEquals("Cannot find issue with id 1", response.getMessage());
        verify(issueRepository, never()).save(any(Issue.class));
    }


    @Test
    public void testUpdateIssueTypeSuccess() {
        when(userRepository.findById("testUser")).thenReturn(Optional.of(user));
        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));


        ResponseDto<?> response = plIssueService.updateIssueType("testUser", 1L, "bug");


        assertEquals("success", response.getMessage());
        verify(issueRepository, times(1)).save(issue);
    }


    @Test
    public void testUpdateIssueTypeUserNotPL() {
        user.setUser_type("dev");
        when(userRepository.findById("testUser")).thenReturn(Optional.of(user));


        ResponseDto<?> response = plIssueService.updateIssueType("testUser", 1L, "bug");


        assertEquals("Issue Type can only be changed by PL", response.getMessage());
        verify(issueRepository, never()).save(any(Issue.class));
    }


    @Test
    public void testUpdateIssueTypeIssueNotFound() {
        when(userRepository.findById("testUser")).thenReturn(Optional.of(user));
        when(issueRepository.findById(1L)).thenReturn(Optional.empty());


        ResponseDto<?> response = plIssueService.updateIssueType("testUser", 1L, "bug");


        assertEquals("Cannot find issue with id 1", response.getMessage());
        verify(issueRepository, never()).save(any(Issue.class));
    }
}
