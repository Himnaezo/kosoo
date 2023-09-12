package com.sparta.common.error;

import lombok.Getter;

/**
 * Custom ErrorCode.
 */
@Getter
public enum ErrorCode {

    // 멤버
    INVALID_TOKEN(403, "토큰이 유효하지 않습니다."),

    IN_USED_ID(400, "중복된 ID 입니다."),
    NOT_FOUND_MEMBER(403, "회원을 찾을 수 없습니다."),
    UNAUTHORIZED_MEMBER(400, "로그인을 해주세요."),

    WRONG_PASSWORD(400, "비밀번호가 틀렸습니다."),

    // 게시글
    NOT_MINE(400, "본인의 글만 수정/삭제/취소 가능합니다."),
    NOT_FOUND_POST(400, "해당 게시글이 존재하지 않습니다."),
    WRONG_POST_ID(400, "게시글 번호를 확인 하십시오."),

    // 댓글
    NOT_FOUND_COMMENT(400, "해당 댓글이 존재하지 않습니다."),

    // 좋아요
    LIKE_AGAIN(400, "이미 좋아요를 눌렀습니다."),
    CAN_NOT_MINE(400, "본인의 글에 좋아요 누를 수 없습니다."),
    NOT_FOUND_LIKE(400, "좋아요를 누르지 않았습니다." ),
    CANCEL_YOURS_ONLY(400, "본인의 좋아요만 취소 가능합니다." ),

    // 팔로우
    NOT_FOUND_FOLLOW(400, "팔로우 관계가 아닙니다" ),
    FOLLOW_AGAIN(400, "팔로우가 중복되었습니다.")


    ;

    private final int httpStatus;
    private final String errorMessage;

    ErrorCode(int httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}