package com.example.demo.DTO;


import com.example.demo.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String token;
    private int expr_time;
    private User user;
}