package kr.aling.post.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 게시물 존재 여부를 반환하는 Dto.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class IsExistsPostResponseDto {

    private Boolean isExists;
}
