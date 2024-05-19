
// Show modal for adding a new project
function openModal(content) {
    let modal = '';
    if (content === 'project') {
        modal = 'addProjectModal'
    }
    else if (content === 'issue') {
        modal = 'addIssueModal';
    }
    document.getElementById(modal).style.display = 'block';
}

// Close the modal
function closeModal(content) {
    let modal = '';
    if (content === 'project') {
        modal = 'addProjectModal'
    }
    else if (content === 'issue') {
        modal = 'addIssueModal';
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
function fetchProjects() {
    fetch('/api/projects')
        .then(response => response.json())
        .then(data => {
            const topProjects = data.slice(0, 3); // 한 번만 슬라이스하여 변수에 저장
            updateProjectDropdown(topProjects); // 상위 3개 프로젝트만 드롭다운에 표시
            updateProjectTable(data); // 전체 프로젝트 목록을 테이블에 표시
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

function showLeftNavbar() {
    const projectData = localStorage.getItem('selectedProject');
    if (projectData) {
        try {
            const project = JSON.parse(projectData);
            if (project && project.id) {
                document.getElementById('leftNavBar').style.display = 'block';
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
    fetch('/api/user/profile', { method: 'GET' })
        .then(response => response.json())
        .then(user => {
            console.log(user);  // 응답 출력
            saveUserInfo(user);
            adjustUIBasedOnRole(user.user_type);
        })
        .catch(error => console.error('Error fetching user data:', error));
}



function saveUserInfo(user) {
    sessionStorage.setItem('userId', user.id);
    sessionStorage.setItem('userType', user.user_type);
}

function adjustUIBasedOnRole(userType) {
    if (userType === 'admin') {
        const addProjectButton = document.getElementById('project-modal');
        if (addProjectButton) {
            addProjectButton.style.display = 'block'; // admin한테만 보여주기
        }
    }
}