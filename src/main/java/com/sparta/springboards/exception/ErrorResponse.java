package com.sparta.springboards.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
// 원래는 우리가 29번 라인에 있는 메서드만 존재했잖아요?
// 그러면 빌더를 사용헀을떄 이렇게 생성자가 없으면 AllArgsConstructor가 암묵적으로 적용되는거임
//근데 우리가 만들었어 그래서 AllArgsConstructor 를 넣어줬다 라고 할뻔 추측임..ㅎ
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;


    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getHttpStatus().value();
        this.code = errorCode.name();
        this.error = errorCode.getHttpStatus().name();
        this.message = errorCode.getMessage();
    }
    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getMessage())
                        .build());
    }
}
