package com.sparta.kosoo.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequestDto {
    private String username;
    private String password;
    private String imageUrl;
    private String introduce;
}
