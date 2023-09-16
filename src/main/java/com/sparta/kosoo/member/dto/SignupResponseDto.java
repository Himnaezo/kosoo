package com.sparta.kosoo.member.dto;

import com.sparta.kosoo.member.entity.Member;
import lombok.Getter;

@Getter
public class SignupResponseDto {
    private String username;
    private String password;

    public SignupResponseDto(Member member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
    }
}
