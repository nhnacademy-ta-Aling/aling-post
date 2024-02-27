package kr.aling.post.normalpost.dto.response;

import kr.aling.post.post.dto.response.ReadPostResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 일반 게시물 조회시 반환하는 응답 객체 일반 게시물 엔티티에 연관된 게시물 번호에 해당하는 게시물 응답 객체와 작성한 유저 번호를 담고 있습니다.
 *
 * @author : 이성준
 * @see kr.aling.post.post.dto.response.ReadPostResponseDto
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class ReadNormalPostResponseDto {

    private ReadPostResponseDto post;
    private Long userNo;

}
