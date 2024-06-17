package com.sparta.reviewspotproject.controller;

import com.sparta.reviewspotproject.security.UserDetailsImpl;
import com.sparta.reviewspotproject.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    // 게시물 좋아요
    @PostMapping("/post/{postId}/likes")
    public ResponseEntity<String> postLikePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likesService.postLikePost(postId, userDetails.getUser());
        return ResponseEntity.ok("게시물의 좋아요 등록 완료");
    }

    // 게시물 좋아요 취소
    @DeleteMapping("/post/{postId}/unlikes")
    public ResponseEntity<String> postUnlikePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likesService.postUnlikePost(postId, userDetails.getUser());
        return ResponseEntity.ok("게시물의 좋아요 취소 완료");
    }

    // 댓글 좋아요
    @PostMapping("/comment/{commentId}/likes")
    public ResponseEntity<String> likePost(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likesService.commentLikePost(commentId, userDetails.getUser());
        return ResponseEntity.ok("댓글의 좋아요 등록 완료");
    }

    // 댓글 좋아요 취소
    @DeleteMapping("/comment/{commentId}/unlikes")
    public ResponseEntity<String> unlikePost(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likesService.commentUnlikePost(commentId, userDetails.getUser());
        return ResponseEntity.ok("댓글의 좋아요 취소 완료");
    }


}
