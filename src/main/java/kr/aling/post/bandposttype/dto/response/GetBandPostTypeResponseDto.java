package kr.aling.post.bandposttype.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 그룹 게시글 분류 정보를 조회하기 위한 dto.
 *
 * @author 정유진
 * @since 1.0
 **/
@Getter
@AllArgsConstructor
public class GetBandPostTypeResponseDto {

    private String name;
}
