# Issue Management System (IMS)

## 소개
Issue Management System (IMS)은 프로젝트 이슈를 관리하기 위한 웹 애플리케이션입니다. 이 애플리케이션은 이슈를 추가, 조회, 수정 및 삭제할 수 있는 기능을 제공합니다. 또한, 이슈를 담당자에게 할당하고 상태를 변경할 수 있습니다.

## 요구사항
- Java 11 이상
- MySQL

## 설치 및 실행 방법
1. **소스 코드 클론:**
   git clone <your-repo-url>
   cd <your-repo-directory>

2. **MySQL 데이터베이스 설정:**
MySQL에 접속하여 데이터베이스와 사용자 계정을 생성합니다.
CREATE TABLE user (
    id varchar(255) NOT NULL PRIMARY KEY,
    name varchar(255),
    password varchar(255) NOT NULL,
    tel varchar(20),
    user_type varchar(255) CHECK (user_type IN ('pl', 'tester', 'admin', 'dev')),
    career int (20),
    token varchar(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    edited_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login_at datetime(6));

CREATE TABLE project (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    creation_time DATETIME,
    last_modified_time DATETIME
);

CREATE TABLE Issue (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description TEXT,
    status VARCHAR(10) NOT NULL,
    priority varchar(25),
    reported_time DATETIME,
    last_modified_time DATETIME,
    reporter_id VARCHAR(30),
    assignee_id VARCHAR(30),
    fixer_id VARCHAR(30),
    project_id BIGINT,
    CONSTRAINT fk_reporter FOREIGN KEY (reporter_id) REFERENCES User(id),
    CONSTRAINT fk_assignee FOREIGN KEY (assignee_id) REFERENCES User(id),
    CONSTRAINT fk_fixer FOREIGN KEY (fixer_id) REFERENCES User(id),
    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES Project(id)
);

CREATE TABLE comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    issue_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    creation_time DATETIME NOT NULL,
    FOREIGN KEY (issue_id) REFERENCES issue(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
)

3.**백엔드 환경 설정:**
‘src/main/resources/application.properties’ 파일을 생성하고 다음과 같이 설정합니다.

spring.datasource.url=jdbc:mysql://localhost:3306/{your database name}
spring.datasource.username={user name}
spring.datasource.password={password}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect



4.**브라우저에서 애플리케이션 열기:**
브라우저에서 http://localhost:8080에 접속하여 애플리케이션을 사용합니다.

5.**사용 방법:**
회원가입:
애플리케이션에 접속한 후 ADMIN, PL, TESTER, DEV의 계정을 생성합니다.

로그인:
애플리케이션에 접속한 후 로그인합니다.

프로젝트 선택:
로그인 후 프로젝트를 선택합니다.

이슈 조회:
이슈 페이지로 이동하여 모든 이슈를 조회합니다. 검색 조건을 입력하여 특정 이슈를 검색할 수 있습니다.

이슈 추가:
'Add Issue' 버튼을 클릭하여 새로운 이슈를 추가합니다.

담당자 할당:
이슈 목록에서 담당자를 할당할 이슈를 선택하고 담당자를 할당합니다.

상태 변경:
이슈의 상태를 변경하여 진행 상황을 업데이트합니다.
