package com.example.demo.Service;

import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.SearchCondition;
import com.example.demo.Repository.IssueRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserIssueServiceTest {

    @InjectMocks
    private UserIssueService userIssueService = new UserIssueService() {
        // Abstract class instantiation with empty implementation
        @Override
        public ResponseDto<?> updateStatus(Long issueId, String newStatus, String userId) {
            return null;
        }
    };

    @Mock
    private IssueRepository issueRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBrowseIssue_NoConditions() {
        Long projectId = 1L;
        SearchCondition condition = new SearchCondition();

        List<Issue> issues = new ArrayList<>();
        when(issueRepository.findByProjectId(projectId)).thenReturn(issues);

        ResponseDto<List<Issue>> response = userIssueService.browseIssue(projectId, condition);
        assertEquals("list", response.getMessage());
        assertEquals(issues, response.getData());
    }

    @Test
    public void testBrowseIssue_WithPriority() {
        Long projectId = 1L;
        SearchCondition condition = new SearchCondition();
        condition.setPriority("high");

        List<Issue> issues = new ArrayList<>();
        when(issueRepository.findByProjectIdAndPriority(projectId, "high")).thenReturn(issues);

        ResponseDto<List<Issue>> response = userIssueService.browseIssue(projectId, condition);
        assertEquals("list", response.getMessage());
        assertEquals(issues, response.getData());
    }

    @Test
    public void testBrowseIssue_WithStatus() {
        Long projectId = 1L;
        SearchCondition condition = new SearchCondition();
        condition.setStatus("open");

        List<Issue> issues = new ArrayList<>();
        when(issueRepository.findByProjectIdAndStatus(projectId, "open")).thenReturn(issues);

        ResponseDto<List<Issue>> response = userIssueService.browseIssue(projectId, condition);
        assertEquals("list", response.getMessage());
        assertEquals(issues, response.getData());
    }

    @Test
    public void testBrowseIssue_WithReporter() {
        Long projectId = 1L;
        SearchCondition condition = new SearchCondition();
        condition.setReporter("reporter1");

        List<Issue> issues = new ArrayList<>();
        when(issueRepository.findByProjectIdAndReporterId(projectId, "reporter1")).thenReturn(issues);

        ResponseDto<List<Issue>> response = userIssueService.browseIssue(projectId, condition);
        assertEquals("list", response.getMessage());
        assertEquals(issues, response.getData());
    }

    @Test
    public void testBrowseIssue_WithAssignee() {
        Long projectId = 1L;
        SearchCondition condition = new SearchCondition();
        condition.setAssignee("assignee1");

        List<Issue> issues = new ArrayList<>();
        when(issueRepository.findByProjectIdAndAssigneeId(projectId, "assignee1")).thenReturn(issues);

        ResponseDto<List<Issue>> response = userIssueService.browseIssue(projectId, condition);
        assertEquals("list", response.getMessage());
        assertEquals(issues, response.getData());
    }
}
