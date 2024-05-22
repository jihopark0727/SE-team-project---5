package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;
import java.util.Date;

@Data
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    private String name;
    private String description;

    private Date creation_time;
    private Date last_modified_time;

    public Project(Long id){
        this.id = id;
    }

    public Project(){}

}
