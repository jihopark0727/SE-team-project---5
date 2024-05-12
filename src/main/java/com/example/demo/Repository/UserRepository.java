package com.example.demo.Repository;

import com.example.demo.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
//import org.springframework.stereotype.Repository;

//@Repository
//@NoRepositoryBean
public interface UserRepository extends JpaRepository<UserEntity, String> {

}