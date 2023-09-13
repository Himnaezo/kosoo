package com.sparta.common.error;

import lombok.Getter;

/**
 * BlogErrorCode.
 */
@Getter
public enum ErrorCode {


    INVALID_TOKEN(403, "토큰이 유효하지 않습니다. 다시 로그인 해주세요."),
    UNAUTHORIZED_MEMBER(400, "로그인 또는 회원가입이 필요합니다."),
    NOT_FOUND_MEMBER(403, "사용자를 찾을 수 없습니다."),
    IN_USED_ID(400, "중복된 ID 입니다."),
    WRONG_PASSWORD(400, "비밀번호를 다시 입력바랍니다."),

    NOT_YOUR_POST(400, "작성자(본인)만 수정/삭제/취소 할 수 있습니다."),

    NOT_FOUND_POST(400, "요청한 게시글이 존재하지 않습니다."),
    NOT_FOUND_COMMENT(400, "요청한 댓글이 존재하지 않습니다."),

    WRONG_POST_ID(400, "게시글 아이디를 확인 하십시오."),

    OVERLAP_LIKE(400, "이미 좋아요를 눌렀습니다."),
    CAN_NOT_YOURSELF(400, "본인이 작성한 게시글/댓글 좋아요를 누를 수 없습니다."),
    NOT_FOUND_LIKE(400, "좋아요를 누르지 않았습니다." ),


    ;

    private final int httpStatus;
    private final String errorMessage;

    ErrorCode(int httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}