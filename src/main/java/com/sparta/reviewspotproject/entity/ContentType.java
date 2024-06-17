package com.sparta.reviewspotproject.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentType {
    POST("게시물"),
    COMMENT("댓글");

    private final String contentType;

}
