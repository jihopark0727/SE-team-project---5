package com.example.demo.Repository;

import com.example.demo.Entity.Project;
import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByIdAndPassword(String id, String password);

    @Query("SELECT u FROM User u JOIN u.projects p WHERE p.id = :projectId")
    List<User> findUsersByProjectId(Long projectId);
}
