package com.sparta.reviewspotproject.service;

import com.sparta.reviewspotproject.entity.*;
import com.sparta.reviewspotproject.repository.CommentRepository;
import com.sparta.reviewspotproject.repository.LikesRepository;
import com.sparta.reviewspotproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 게시물의 좋아요 기능
    @Transactional
    public int postLikePost(Long postId, User user) {
        Post post = findPostId(postId);

        // 사용자가 게시물의 작성자인지 확인
        if (post.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("자신의 게시물에 좋아요를 할 수 없습니다.");
        }
        // 사용자가 이미 좋아요를 했는지 확인
        if (likesRepository.findByPostIdAndUserId(postId, user.getId()).isPresent()) {
            throw new IllegalArgumentException("중복 좋아요는 할 수 없습니다.");
        } else {
            post.setPostLikesCount(post.getPostLikesCount() + 1);
            likesRepository.save(new Likes(post, user, ContentType.POST));
        }
        return post.getPostLikesCount();
    }

    // 게시물의 좋아요 취소 기능
    @Transactional
    public int postUnlikePost(Long postId, User user) {
        Post post = findPostId(postId);

        // 사용자가 게시물에 좋아요를 했는지 확인
        Likes like = likesRepository.findByPostIdAndUserId(postId, user.getId()).orElseThrow(() ->
                new IllegalArgumentException("해당 게시물에 좋아요를 하지 않았습니다."));

        post.setPostLikesCount(post.getPostLikesCount() - 1);
        likesRepository.delete(like);
        return post.getPostLikesCount();
    }

    private Post findPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시물은 존재하지 않습니다."));
    }

    // 댓글의 좋아요 기능
    @Transactional
    public int commentLikePost(Long commentId, User user) {
        Comment comment = findCommentId(commentId);

        // 사용자가 게시물의 작성자인지 확인
        if (comment.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("자신의 댓글에 좋아요를 할 수 없습니다.");
        }
        // 사용자가 이미 좋아요를 했는지 확인
        if (likesRepository.findByCommentIdAndUserId(commentId, user.getId()).isPresent()) {
            throw new IllegalArgumentException("중복 좋아요는 할 수 없습니다.");
        } else {
            comment.setCommentLikesCount(comment.getCommentLikesCount() + 1);
            likesRepository.save(new Likes(comment, user, ContentType.COMMENT));
        }
        return comment.getCommentLikesCount();
    }

    // 댓글의 좋아요 취소 기능
    @Transactional
    public int commentUnlikePost(Long commentId, User user) {
        Comment comment = findCommentId(commentId);

        // 사용자가 게시물에 좋아요를 했는지 확인
        Likes like = likesRepository.findByCommentIdAndUserId(commentId, user.getId()).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글에 좋아요를 하지 않았습니다."));

        comment.setCommentLikesCount(comment.getCommentLikesCount() - 1);
        likesRepository.delete(like);
        return comment.getCommentLikesCount();
    }

    private Comment findCommentId(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글은 존재하지 않습니다."));
    }

}
