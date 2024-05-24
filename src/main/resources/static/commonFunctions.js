// Show modal for adding a new project
function openModal(content) {
    let modal = '';
    if (content === 'project') {
        modal = 'addProjectModal';
    } else if (content === 'issue') {
        modal = 'addIssueModal';
    } else if (content === 'comment') {
        modal = 'addCommentModal';
    } else if (content === 'assignDev') {
        modal = 'assignDevModal';
    } else if (content === 'assignFixer') {  // 추가된 부분
        modal = 'assignFixerModal';
    } else if (content === 'user') {
        modal = 'addUserModal';
    }
    document.getElementById(modal).style.display = 'block';
}

// Close the modal
function closeModal(content) {
    let modal = '';
    if (content === 'project') {
        modal = 'addProjectModal';
    } else if (content === 'issue') {
        modal = 'addIssueModal';
    } else if (content === 'comment') {
        modal = 'addCommentModal';
    } else if (content === 'assignDev') {
        modal = 'assignDevModal';
    } else if (content === 'assignFixer') {  // 추가된 부분
        modal = 'assignFixerModal';
    } else if (content === 'user') {
        modal = 'addUserModal';
    }
    document.getElementById(modal).style.display = 'none';
}

function formatDate(dateString) {
    if (!dateString) return 'No Date Provided'; // 날짜 데이터가 없을 경우
    const date = new Date(dateString);
    if (isNaN(date.getTime())) {  // 유효하지 않은 날짜인지 확인
        return 'Invalid Date';
    }
    const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit' };
    return date.toLocaleDateString('en-US', options);
}

function showDropdown(item) {
    document.getElementById(item).style.display = 'block';
}

function hideDropdown(item) {
    document.getElementById(item).style.display = 'none';
}

// Fetch projects from the server and update UI components
function fetchProjectList() {
    fetch('/api/projects')
        .then(response => response.json())
        .then(projects => {
            const topProjects = projects.slice(0, 3); // 한 번만 슬라이스하여 변수에 저장
            updateProjectDropdown(topProjects); // 상위 3개 프로젝트만 드롭다운에 표시
        })
        .catch(error => console.error('Error loading the projects:', error));
}

// Update the dropdown menu with fetched projects
function updateProjectDropdown(projects) {
    const dropdown = document.getElementById('projectDropdown');
    dropdown.innerHTML = ''; // 기존 목록을 지웁니다.

    projects.forEach(project => {
        const projectLink = document.createElement('a');
        projectLink.href = '#';
        projectLink.textContent = project.name;
        projectLink.onclick = () => selectProject(project.id);
        dropdown.appendChild(projectLink);
    });

    // '모든 프로젝트 보기' 링크 추가
    const allProjectsLink = document.createElement('a');
    allProjectsLink.href = '#';
    allProjectsLink.textContent = '모든 프로젝트 보기';
    allProjectsLink.onclick = goAllProjects; // 이 함수는 모든 프로젝트를 보는 페이지로 이동하도록 정의해야 함
    dropdown.appendChild(allProjectsLink);
}

// 모든 프로젝트를 보는 페이지로 이동하는 함수
function goAllProjects() {
    window.location.href = 'all-projects.html'; // 'all-projects.html' 페이지로 이동
}

// 선택된 프로젝트를 localStorage에 저장하고 이슈 페이지로 이동
function selectProject(projectId) {
    fetch(`/api/projects/${projectId}`)
        .then(response => response.json())
        .then(project => {
            localStorage.setItem('selectedProject', JSON.stringify(project));
            window.location.href = 'issue.html';
        })
        .catch(error => console.error('Error selecting the project:', error));
}

function selectIssue(issueId) {
    fetch(`/api/projects/${getSelectedProject().id}/issues/${issueId}`)
        .then(response => response.json())
        .then(issue => {
            localStorage.setItem('selectedIssue', JSON.stringify(issue));
            window.location.href = 'comments.html';
        })
        .catch(error => console.error('Error selecting the issue:', error));
}

function showLeftNavbar() {
    const projectData = localStorage.getItem('selectedProject');
    console.log(localStorage);
    if (projectData) {
        try {
            const project = JSON.parse(projectData);
            if (project && project.id) {
                document.getElementById('leftNavBar').style.display = 'flex';
            } else {
                document.getElementById('leftNavBar').style.display = 'none';
            }
        } catch (e) {
            console.error('Error parsing project data:', e);
            document.getElementById('leftNavBar').style.display = 'none';
            localStorage.removeItem('selectedProject');  // Remove corrupt data
        }
    } else {
        document.getElementById('leftNavBar').style.display = 'none';
    }
}

function fetchUserProfile() {
    fetch('/api/users/profile', { method: 'GET' })
        .then(response => response.json())
        .then(user => {
            console.log(user);
            localStorage.setItem('user', JSON.stringify(user));
            adjustUser();
        })
        .catch(error => console.error('Error fetching user data:', error));
}

function adjustUIBasedOnRole(userType) {
    const addProjectButton = document.getElementById('project-modal-btn');
    const addIssueButton = document.getElementById('issue-modal-btn');
    const addUserButton = document.getElementById('user-modal-btn');
    const addCommentButton = document.getElementById('comment-modal-btn');

    if (userType === 'admin') {
        if (addProjectButton) {
            addProjectButton.style.display = 'block'; // admin한테만 보여주기
        }
        if (addUserButton) {
            addUserButton.style.display = 'block';
        }
    }
    if (userType === 'tester') {
        if (addIssueButton) {
            addIssueButton.style.display = 'block'; // tester한테만 보여주기
        }
    }
    if (addCommentButton) {
        addCommentButton.style.display = 'block'; // 모든 유저에게 보여주기
    }
}

function showUserInfo(user) {
    document.getElementById('userIdDisplay').innerText = user.id + ' ' + user.user_type;
}

function adjustUser() {
    let storedUser = localStorage.getItem('user');  // 로컬 스토리지에서 사용자 정보 불러오기
    if (storedUser) {
        user = JSON.parse(storedUser);
        // 사용자 정보 상단 바에 띄우기
        showUserInfo(user);
        // 사용자에 맞게 UI 수정하기
        adjustUIBasedOnRole(user.user_type);
    }
}

function getUserId() {
    const userId = JSON.parse(localStorage.getItem('user')).id;
    return userId;
}

function getSelectedProject() {
    const selectedProject = JSON.parse(localStorage.getItem('selectedProject'));
    return selectedProject;
}

function getSelectedIssue() {
    const selectedIssue = JSON.parse(localStorage.getItem('selectedIssue'));
    return selectedIssue;
}

function logout() {
    fetch('/api/auth/logout', { method: 'POST' })
        .then(response => {
            if (response.ok) {
                window.location.href = '/index.html?logout=success'; // Redirect to login on successful logout
            } else {
                response.text().then(text => alert('Failed to logout: ' + text)); // 서버로부터의 실패 응답을 출력
            }
        })
        .catch(error => {
            console.error('Logout failed', error);
            alert('Logout error: ' + error.message);
        });
}

function setSelectedProject() {
    selectedProject = getSelectedProject();
    if (!selectedProject) {
        document.getElementById('issueHeader').textContent = 'No project selected';
        return;
    }
    projectName = selectedProject.name;
    projectId = selectedProject.id;
    document.getElementById('project-btn').innerText='Project: ' + projectName;
}

function setSelectedIssue() {
    selectedIssue = getSelectedIssue();
    if (!selectedIssue) {
        document.getElementById('commentHeader').textContent = 'No Issue selected';
        return;
    }
    issueTitle = selectedIssue.title;
    issueId = selectedIssue.id;
    document.getElementById('commentHeader').innerText='Issue: ' + issueTitle;
}

function commonLoad() {
    adjustUser();
    fetchProjectList();
    showLeftNavbar();
}
