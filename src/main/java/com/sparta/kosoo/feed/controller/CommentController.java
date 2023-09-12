package com.sparta.kosoo.feed.controller;

import com.sparta.common.config.security.MemberDetailsImpl;
import com.sparta.common.error.ErrorCode;
import com.sparta.common.error.exception.CustomException;
import com.sparta.common.result.ApiResult;
import com.sparta.kosoo.feed.dto.CommentRequestDto;
import com.sparta.kosoo.feed.dto.CommentResponseDto;
import com.sparta.kosoo.feed.dto.CommentUpdateRequestDto;
import com.sparta.kosoo.feed.service.CommentLikeService;
import com.sparta.kosoo.feed.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentLikeService commentLikeService;

    @PostMapping("/comments")
    public ResponseEntity<?> createComment(@AuthenticationPrincipal MemberDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto){
        checkToken(userDetails);
        return ResponseEntity.ok(commentService.createComment(userDetails, requestDto));

    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @AuthenticationPrincipal MemberDetailsImpl userDetails, @RequestBody CommentUpdateRequestDto requestDto){
        checkToken(userDetails);
        return ResponseEntity.ok(commentService.updateComment(commentId, userDetails, requestDto));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal MemberDetailsImpl userDetails) {
        checkToken(userDetails);
        commentService.deleteComment(commentId, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body("👌 댓글 삭제 성공");
    }

    @PostMapping("/comments/likes/{commentId}")
    public CommentResponseDto commentLike(@AuthenticationPrincipal MemberDetailsImpl userDetails, @PathVariable Long commentId) {
        return commentLikeService.commentLike(userDetails, commentId);
    }

    @DeleteMapping("/comments/likes/{commentId}")
    public ApiResult deleteCommentLike(@AuthenticationPrincipal MemberDetailsImpl userDetails, @PathVariable Long commentId){
        return commentLikeService.deleteCommentLike(userDetails, commentId);
    }

    private void checkToken(MemberDetailsImpl userDetails) {
        if (userDetails == null) throw new CustomException(ErrorCode.NOT_FOUND_MEMBER,null);
    }

}
