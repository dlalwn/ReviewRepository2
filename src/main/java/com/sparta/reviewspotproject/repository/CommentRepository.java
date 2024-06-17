package com.sparta.reviewspotproject.repository;

import com.sparta.reviewspotproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

