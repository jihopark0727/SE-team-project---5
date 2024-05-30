package com.example.demo.Service;

import com.example.demo.DTO.IssueDto;
import com.example.demo.DTO.ResponseDto;
import com.example.demo.Entity.Issue;
import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;
import com.example.demo.Repository.IssueRepository;
import com.example.demo.Repository.ProjectRepository;
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
public class TesterIssueServiceTest {

    @InjectMocks
    private TesterIssueService testerIssueService;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddIssue_Success() {
        IssueDto issueDto = new IssueDto();
        issueDto.setTitle("Test Issue");
        issueDto.setDescription("Description");
        Long projectId = 1L;
        String reporterId = "reporter1";
        String priority = "High";

        Project project = new Project();
        User reporter = new User();
        reporter.setId(reporterId);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(userRepository.findById(reporterId)).thenReturn(Optional.of(reporter));

        ResponseDto<Issue> responseDto = testerIssueService.addIssue(issueDto, projectId, reporterId, priority);

        assertNotNull(responseDto);
        assertEquals("Add issue success", responseDto.getMessage());
        assertNotNull(responseDto.getData());
        verify(issueRepository, times(1)).save(any(Issue.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddIssue_InvalidProjectId() {
        IssueDto issueDto = new IssueDto();
        Long projectId = 1L;
        String reporterId = "reporter1";
        String priority = "High";

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        testerIssueService.addIssue(issueDto, projectId, reporterId, priority);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddIssue_InvalidReporterId() {
        IssueDto issueDto = new IssueDto();
        Long projectId = 1L;
        String reporterId = "reporter1";
        String priority = "High";

        Project project = new Project();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(userRepository.findById(reporterId)).thenReturn(Optional.empty());

        testerIssueService.addIssue(issueDto, projectId, reporterId, priority);
    }

    @Test
    public void testUpdateStatus_Success() {
        Long issueId = 1L;
        String newStatus = "closed";
        String userId = "reporter1";

        Issue issue = new Issue();
        issue.setReporterId(userId);
        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));

        ResponseDto<?> responseDto = testerIssueService.updateStatus(issueId, newStatus, userId);

        assertNotNull(responseDto);
        assertEquals("Issue status updated successfully", responseDto.getMessage());
        verify(issueRepository, times(1)).save(issue);
    }

    @Test
    public void testUpdateStatus_Failed_IssueNotFound() {
        Long issueId = 1L;
        String newStatus = "closed";
        String userId = "reporter1";

        when(issueRepository.findById(issueId)).thenReturn(Optional.empty());

        ResponseDto<?> responseDto = testerIssueService.updateStatus(issueId, newStatus, userId);

        assertNotNull(responseDto);
        assertEquals("Issue not found", responseDto.getMessage());
        verify(issueRepository, times(0)).save(any(Issue.class));
    }

    @Test
    public void testUpdateStatus_Failed_OnlyReporterCanResolve() {
        Long issueId = 1L;
        String newStatus = "resolved";
        String userId = "notReporter";

        Issue issue = new Issue();
        issue.setReporterId("reporter1");
        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));

        ResponseDto<?> responseDto = testerIssueService.updateStatus(issueId, newStatus, userId);

        assertNotNull(responseDto);
        assertEquals("Only the reporter can change the status to resolved", responseDto.getMessage());
        verify(issueRepository, times(0)).save(any(Issue.class));
    }
}
