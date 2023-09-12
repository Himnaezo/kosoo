package com.sparta.kosoo.feed.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long feedId;
    private String content;
}
