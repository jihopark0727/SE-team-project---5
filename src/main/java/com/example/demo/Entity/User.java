package com.example.demo.Entity;

import com.example.demo.DTO.SignUpDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.Set;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")				// 본인 테이블명과 맞춰주어야 함
public class User {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToMany(mappedBy = "users")
    private Set<Project> projects;

    private String password;
    private String name;
    private String tel;
    private String userType;
    private int career;
    private String token;
    private LocalDateTime created_at;
    private LocalDateTime edited_at;
    private LocalDateTime last_login_at;

    public User(String id){
        this.id = id;
    }

    public User(SignUpDto dto) {
        this.id = dto.getId();
        this.password = dto.getPassword();
        this.name = dto.getName();
        this.tel = dto.getTel();
        this.userType = dto.getUser_type();
        this.career = dto.getCareer();
        this.token = "";
        this.created_at = LocalDateTime.now();
        this.edited_at = LocalDateTime.now();
    }

}
