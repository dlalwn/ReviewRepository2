package com.sparta.reviewspotproject.repository;

import com.sparta.reviewspotproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String username);
    Optional<User> findByEmail(String email);
}
