package com.sparta.common.result;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ApiResult {

    private int httpStatus;
    private String ResultMessage;

    @Builder
    public ApiResult(String ResultMessage, int httpStatus) {

        this.httpStatus = httpStatus;
        this.ResultMessage = ResultMessage;
    }
}