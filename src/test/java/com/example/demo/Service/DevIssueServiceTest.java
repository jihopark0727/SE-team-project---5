package com.example.demo.Service;

import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Repository.IssueRepository;
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
public class DevIssueServiceTest {

    @InjectMocks
    private DevIssueService devIssueService;

    @Mock
    private IssueRepository issueRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateStatus_Success_Fixed() {
        Long issueId = 1L;
        String newStatus = "fixed";
        String userId = "assignee1";

        Issue issue = new Issue();
        issue.setId(issueId);
        issue.setAssigneeId(userId);

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));

        ResponseDto<?> response = devIssueService.updateStatus(issueId, newStatus, userId);

        assertNotNull(response);
        assertEquals("Issue status updated successfully", response.getMessage());
        assertEquals(newStatus, issue.getStatus());
        assertEquals(userId, issue.getFixerId());
        verify(issueRepository, times(1)).findById(issueId);
        verify(issueRepository, times(1)).save(issue);
    }

    @Test
    public void testUpdateStatus_Failed_NotAssignee() {
        Long issueId = 1L;
        String newStatus = "fixed";
        String userId = "notAssignee";

        Issue issue = new Issue();
        issue.setId(issueId);
        issue.setAssigneeId("assignee1");

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));

        ResponseDto<?> response = devIssueService.updateStatus(issueId, newStatus, userId);

        assertNotNull(response);
        assertEquals("Only the assignee can change the status to fixed", response.getMessage());
        verify(issueRepository, times(1)).findById(issueId);
        verify(issueRepository, times(0)).save(issue);
    }

    @Test
    public void testUpdateStatus_Success_OtherStatus() {
        Long issueId = 1L;
        String newStatus = "in_progress";
        String userId = "assignee1";

        Issue issue = new Issue();
        issue.setId(issueId);
        issue.setAssigneeId(userId);

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));

        ResponseDto<?> response = devIssueService.updateStatus(issueId, newStatus, userId);

        assertNotNull(response);
        assertEquals("Issue status updated successfully", response.getMessage());
        assertEquals(newStatus, issue.getStatus());
        assertNull(issue.getFixerId());
        verify(issueRepository, times(1)).findById(issueId);
        verify(issueRepository, times(1)).save(issue);
    }

    @Test
    public void testUpdateStatus_Failed_IssueNotFound() {
        Long issueId = 1L;
        String newStatus = "fixed";
        String userId = "assignee1";

        when(issueRepository.findById(issueId)).thenReturn(Optional.empty());

        ResponseDto<?> response = devIssueService.updateStatus(issueId, newStatus, userId);

        assertNotNull(response);
        assertEquals("Issue not found", response.getMessage());
        verify(issueRepository, times(1)).findById(issueId);
        verify(issueRepository, times(0)).save(any(Issue.class));
    }
}
