package kr.aling.post.bandposttype.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 기본 그룹 게시글 분류를 생성하기 위한 요청 dto.
 *
 * @author : 정유진
 * @since : 1.0
 **/
@Getter
@NoArgsConstructor
public class CreateBandPostTypeDefaultRequestDto {
    private Long bandNo;
}
