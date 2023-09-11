package com.sparta.kosoo.feed.dto;

import com.sparta.kosoo.feed.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto{
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String username;
    private int heartNum;

    public CommentResponseDto(Comment comment, String username) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.username = username;
    }
    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.username = comment.getMember().getUsername();
        this.heartNum = comment.getCommentLikeList().size();
    }
}
