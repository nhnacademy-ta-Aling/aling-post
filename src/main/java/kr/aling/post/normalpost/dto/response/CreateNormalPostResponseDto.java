package kr.aling.post.normalpost.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 일반 게시물 생성시 반환하는 응답 객체 게시물이 정상적으로 생성되어있을 때, 해당 게시물의 번호를 반환합니다.
 *
 * @author : 이성준
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class CreateNormalPostResponseDto {

    private Long postNo;
}
