package com.sparta.kosoo.member.dto;

import lombok.Getter;

@Getter
public class PasswordResponseDto {
    private boolean isCorrect;

    public PasswordResponseDto(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}

