package com.sparta.reviewspotproject.repository;

import com.sparta.reviewspotproject.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByPostIdAndUserId(Long postId, Long userId);
    Optional<Likes> findByCommentIdAndUserId(Long commentId, Long userId);

}
