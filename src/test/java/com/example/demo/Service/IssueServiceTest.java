//package com.example.demo.Service;
//
//import com.example.demo.DTO.ResponseDto;
//import com.example.demo.Entity.Issue;
//import com.example.demo.Entity.Project;
//import com.example.demo.Entity.User;
//import com.example.demo.Repository.IssueRepository;
//import com.example.demo.Repository.ProjectRepository;
//import com.example.demo.Repository.UserRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class IssueServiceTest {
//
//    @InjectMocks
//    private IssueService issueService;
//
//    @Mock
//    private IssueRepository issueRepository;
//
//    @Mock
//    private ProjectRepository projectRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private UserService userService;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testGetIssuesByProjectId() {
//        Long projectId = 1L;
//        List<Issue> issues = List.of(new Issue(), new Issue());
//        when(issueRepository.findByProjectId(projectId)).thenReturn(issues);
//
//        List<Issue> result = issueService.getIssuesByProjectId(projectId);
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        verify(issueRepository, times(1)).findByProjectId(projectId);
//    }
//
//    @Test
//    public void testGetIssueById() {
//        Long issueId = 1L;
//        Issue issue = new Issue();
//        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));
//
//        Optional<Issue> result = issueService.getIssueById(issueId);
//
//        assertTrue(result.isPresent());
//        assertEquals(issue, result.get());
//        verify(issueRepository, times(1)).findById(issueId);
//    }
//
//    @Test
//    public void testAddIssue_Success() {
//        Long projectId = 1L;
//        String reporterId = "reporter1";
//        String priority = "High";
//        Issue issue = new Issue();
//
//        Project project = new Project();
//        User reporter = new User();
//
//        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
//        when(userRepository.findById(reporterId)).thenReturn(Optional.of(reporter));
//        when(issueRepository.save(any(Issue.class))).thenReturn(issue);
//
//        Issue result = issueService.addIssue(issue, projectId, reporterId, priority);
//
//        assertNotNull(result);
//        verify(projectRepository, times(1)).findById(projectId);
//        verify(userRepository, times(1)).findById(reporterId);
//        verify(issueRepository, times(1)).save(issue);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testAddIssue_InvalidProjectId() {
//        Long projectId = 1L;
//        String reporterId = "reporter1";
//        String priority = "High";
//        Issue issue = new Issue();
//
//        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());
//
//        issueService.addIssue(issue, projectId, reporterId, priority);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testAddIssue_InvalidReporterId() {
//        Long projectId = 1L;
//        String reporterId = "reporter1";
//        String priority = "High";
//        Issue issue = new Issue();
//
//        Project project = new Project();
//
//        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
//        when(userRepository.findById(reporterId)).thenReturn(Optional.empty());
//
//        issueService.addIssue(issue, projectId, reporterId, priority);
//    }
//
//    @Test
//    public void testAssignDevToIssue_Success() {
//        Long issueId = 1L;
//        String assigneeId = "assignee1";
//        Issue issue = new Issue();
//        issue.setId(issueId);
//
//        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));
//
//        ResponseDto<?> response = issueService.assignDevToIssue(issueId, assigneeId);
//
//        assertNotNull(response);
//        assertEquals("Assignee updated successfully", response.getMessage());
//        verify(issueRepository, times(1)).findById(issueId);
//        verify(issueRepository, times(1)).save(issue);
//    }
//
//    @Test
//    public void testAssignDevToIssue_IssueNotFound() {
//        Long issueId = 1L;
//        String assigneeId = "assignee1";
//
//        when(issueRepository.findById(issueId)).thenReturn(Optional.empty());
//
//        ResponseDto<?> response = issueService.assignDevToIssue(issueId, assigneeId);
//
//        assertNotNull(response);
//        assertEquals("Issue not found", response.getMessage());
//        verify(issueRepository, times(1)).findById(issueId);
//        verify(issueRepository, times(0)).save(any(Issue.class));
//    }
//
//    @Test
//    public void testAssignFixerToIssue_Success() {
//        Long issueId = 1L;
//        String fixerId = "fixer1";
//        Issue issue = new Issue();
//        issue.setId(issueId);
//        issue.setAssigneeId(fixerId);
//
//        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));
//
//        ResponseDto<?> response = issueService.assignFixerToIssue(issueId, fixerId);
//
//        assertNotNull(response);
//        assertEquals("Fixer assigned and issue fixed", response.getMessage());
//        verify(issueRepository, times(1)).findById(issueId);
//        verify(issueRepository, times(1)).save(issue);
//    }
//
//    @Test
//    public void testAssignFixerToIssue_NotAssignedDev() {
//        Long issueId = 1L;
//        String fixerId = "fixer1";
//        Issue issue = new Issue();
//        issue.setId(issueId);
//        issue.setAssigneeId("otherDev");
//
//        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));
//
//        ResponseDto<?> response = issueService.assignFixerToIssue(issueId, fixerId);
//
//        assertNotNull(response);
//        assertEquals("Only the assigned dev can be the fixer", response.getMessage());
//        verify(issueRepository, times(1)).findById(issueId);
//        verify(issueRepository, times(0)).save(any(Issue.class));
//    }
//
//    @Test
//    public void testUpdateIssueStatus_Success() {
//        Long issueId = 1L;
//        String newStatus = "resolved";
//        String userId = "reporter1";
//        Issue issue = new Issue();
//        issue.setId(issueId);
//        issue.setReporterId(userId);
//
//        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));
//
//        ResponseDto<?> response = issueService.updateIssueStatus(issueId, newStatus, userId);
//
//        assertNotNull(response);
//        assertEquals("Issue status updated successfully", response.getMessage());
//        verify(issueRepository, times(1)).findById(issueId);
//        verify(issueRepository, times(1)).save(issue);
//    }
//
//    @Test
//    public void testUpdateIssueStatus_Failed_NotReporter() {
//        Long issueId = 1L;
//        String newStatus = "resolved";
//        String userId = "notReporter";
//        Issue issue = new Issue();
//        issue.setId(issueId);
//        issue.setReporterId("reporter1");
//
//        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));
//
//        ResponseDto<?> response = issueService.updateIssueStatus(issueId, newStatus, userId);
//
//        assertNotNull(response);
//        assertEquals("Only the reporter can change the status to resolved", response.getMessage());
//        verify(issueRepository, times(1)).findById(issueId);
//        verify(issueRepository, times(0)).save(any(Issue.class));
//    }
//
//    @Test
//    public void testUpdateIssueStatus_Failed_NotProjectLeader() {
//        Long issueId = 1L;
//        String newStatus = "closed";
//        String userId = "notPL";
//        Issue issue = new Issue();
//        issue.setId(issueId);
//
//        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));
//        when(userService.isUserPL(userId)).thenReturn(false);
//
//        ResponseDto<?> response = issueService.updateIssueStatus(issueId, newStatus, userId);
//
//        assertNotNull(response);
//        assertEquals("Only the project leader can change the status to closed", response.getMessage());
//        verify(issueRepository, times(1)).findById(issueId);
//        verify(userService, times(1)).isUserPL(userId);
//        verify(issueRepository, times(0)).save(any(Issue.class));
//    }
//
//    @Test
//    public void testUpdateIssueStatus_Success_Reopened() {
//        Long issueId = 1L;
//        String newStatus = "reopened";
//        String userId = "reporter1";
//        Issue issue = new Issue();
//        issue.setId(issueId);
//
//        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));
//
//        ResponseDto<?> response = issueService.updateIssueStatus(issueId, newStatus, userId);
//
//        assertNotNull(response);
//        assertEquals("Issue status updated successfully", response.getMessage());
//        assertNull(issue.getAssigneeId());
//        assertNull(issue.getFixerId());
//        verify(issueRepository, times(1)).findById(issueId);
//        verify(issueRepository, times(1)).save(issue);
//    }
//
//    @Test
//    public void testUpdateIssueStatus_Failed_IssueNotFound() {
//        Long issueId = 1L;
//        String newStatus = "resolved";
//        String userId = "reporter1";
//
//        when(issueRepository.findById(issueId)).thenReturn(Optional.empty());
//
//        ResponseDto<?> response = issueService.updateIssueStatus(issueId, newStatus, userId);
//
//        assertNotNull(response);
//        assertEquals("Issue not found", response.getMessage());
//        verify(issueRepository, times(1)).findById(issueId);
//        verify(issueRepository, times(0)).save(any(Issue.class));
//    }
//}
