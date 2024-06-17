package com.sparta.reviewspotproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "likes")
@NoArgsConstructor
public class Likes extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    Comment comment;

    @Column(name = "content_type")
    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    public Likes(Post post, User user, ContentType contentType) {
        this.post = post;
        this.user = user;
        this.contentType = contentType;
    }

    public Likes(Comment comment, User user, ContentType contentType) {
        this.comment = comment;
        this.user = user;
        this.contentType = contentType;
    }

}
