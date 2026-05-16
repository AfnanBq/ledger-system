package com.example.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.model.entity.Users;

import java.util.Optional;
import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    // crud to return user based on email
    Optional<Users> findByEmail(String email);

    // crud to return user based on id
    Optional<Users> findById(Long id);

    // crud to return all users
    List<Users> findAll();
}
