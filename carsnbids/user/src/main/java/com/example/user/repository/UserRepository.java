package com.example.user.repository;


import com.example.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {


    @Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    Optional<Users> findByEmail(String username);
}
