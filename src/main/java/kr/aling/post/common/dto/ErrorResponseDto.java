package kr.aling.post.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 에러 메시지 응답 객체 입니다.
 *
 * @author : 이성준
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    private String message;
}
