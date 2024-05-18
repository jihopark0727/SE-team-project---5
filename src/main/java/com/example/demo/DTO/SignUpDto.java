package com.example.demo.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String id;
    private String name;
    private String password;
    private String confirm_password;
    private String tel;
    private String user_type;
    private String career;
    private LocalDateTime created_at;
    private LocalDateTime edited_at;
    private LocalDateTime last_login_at;
    private String token;
}