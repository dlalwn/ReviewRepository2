package com.sparta.reviewspotproject.dto;

import com.sparta.reviewspotproject.entity.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileResponseDto {
    private String userId;
    private String userName;
    private String email;
    private String tagline;


    public ProfileResponseDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.tagline = user.getTagLine();
    }

}