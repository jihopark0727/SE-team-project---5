package com.example.demo.DTO;

public class LoginDto {
    private String id; // 사용자 이름 또는 이메일
    private String password; // 사용자 비밀번호

    // 기본 생성자
    public LoginDto() {
    }

    // 생성자
    public LoginDto(String username, String password) {
        this.id = username;
        this.password = password;
    }

    // username에 대한 getter
    public String getId() {
        return id;
    }

    // username에 대한 setter
    public void setId(String id) {
        this.id = id;
    }

    // password에 대한 getter
    public String getPassword() {
        return password;
    }

    // password에 대한 setter
    public void setPassword(String password) {
        this.password = password;
    }
}
