package com.sparta.kosoo.member.dto;

import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String username;
    private String introduce;

    public ProfileResponseDto(String username, String introduce) {
        this.username = username;
        this.introduce = introduce;
    }
}