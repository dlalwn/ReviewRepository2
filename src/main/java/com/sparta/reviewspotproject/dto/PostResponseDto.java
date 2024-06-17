package com.sparta.reviewspotproject.dto;

import com.sparta.reviewspotproject.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private Long userId;
    private String title;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private int postLikesCount;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.userId = post.getUser().getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.postLikesCount = post.getPostLikesCount();
    }
}