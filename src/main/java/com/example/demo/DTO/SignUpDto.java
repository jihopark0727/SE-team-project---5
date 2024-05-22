package com.example.demo.DTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    @NotBlank(message = "Id is mandatory")
    private String id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Password is mandatory")
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