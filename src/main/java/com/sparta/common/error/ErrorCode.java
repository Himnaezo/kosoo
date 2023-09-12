package com.sparta.common.error;

import lombok.Getter;

/**
 * Custom ErrorCode.
 */
@Getter
public enum ErrorCode {


    INVALID_TOKEN(403, "토큰이 유효하지 않습니다."),

    UNAUTHORIZED_MEMBER(400, "본인의 글만 수정/삭제/취소 가능합니다."),
    IN_USED_ID(400, "중복된 ID 입니다."),
    NOT_FOUND_MEMBER(403, "회원을 찾을 수 없습니다."),

    WRONG_PASSWORD(400, "비밀번호가 틀렸습니다."),

    NOT_FOUND_POST(400, "요청한 게시글이 존재하지 않습니다."),
    WRONG_POST_ID(400, "게시글 번호를 확인 하십시오."),

    NOT_FOUND_COMMENT(400, "작성한 댓글을 찾을 수 없습니다."),

    OVERLAP_HEART(400, "좋아요를 이미 누르셨습니다."),
    CAN_NOT_MINE(400, "본인의 글에 좋아요 누를 수 없습니다."),
    NOT_FOUND_HEART(400, "좋아요를 누르지 않았습니다." ),
    ;

    private final int httpStatus;
    private final String errorMessage;

    ErrorCode(int httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}