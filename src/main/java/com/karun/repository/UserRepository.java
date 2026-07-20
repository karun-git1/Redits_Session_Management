package com.karun.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.karun.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}