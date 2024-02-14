package kr.aling.post.common.utils;

import kr.aling.post.common.dto.ErrorResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeException;

/**
 * 각 예외에 대한 에러 메시지 응답 객체 생성 유틸 클래스입니다.
 *
 * @author : 이성준
 * @since : 1.0
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponseUtils {
    public static ErrorResponseDto makeResponse(HttpMediaTypeException exception) {
        return new ErrorResponseDto(exception.getSupportedMediaTypes().toString());
    }

    public static ErrorResponseDto makeResponse(RuntimeException exception) {
        return new ErrorResponseDto(exception.getMessage());
    }

    public static ErrorResponseDto makeResponse(BindException exception) {
        return new ErrorResponseDto(exception.getMessage());
    }
}
