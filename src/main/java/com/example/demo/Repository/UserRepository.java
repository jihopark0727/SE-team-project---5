package com.example.demo.Repository;

import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public boolean existsByIdAndPassword(String id, String password);
    @Query("SELECT u FROM User u WHERE u.user_type = :userType")
    List<User> findByUserType(@Param("userType") String userType);

}