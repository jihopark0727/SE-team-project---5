package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Date;

@Data
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private Date creation_time;
    private Date last_modified_time;

    public Project(Long id){
        this.id = id;
    }

    public Project(){}

}
