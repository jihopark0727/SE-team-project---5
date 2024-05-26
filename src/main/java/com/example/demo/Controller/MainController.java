package com.example.demo.Controller;
import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("/")
public class MainController {
    @GetMapping("check")
    public String check() {
        return "Success";
    }
//    @GetMapping("test")
//    public Project getProject(){
//        Project project = new Project();
//        User user = new User();
//        long val = 1;
//        project.setId(val);
//        project.setName("test");
//        project.setUsers(new HashSet<User>());
//        project.getUsers().add(user);
//        user.setId("aaa");
//        user.setName("bbb");
//        user.setUser_type("sdd");
//        user.setCareer(1);
//        user.setPassword("Aaa");
//        user.setProjects(new HashSet<Project>());
//        user.getProjects().add(project);
//        return project;
//    }
}