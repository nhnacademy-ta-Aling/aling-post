package kr.aling.post.bandpost.dto.response.external;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * User 서버에 게시글 작성자(유저) 조회 API 응답 Dto.
 *
 * @author 박경서
 * @since 1.0
 **/
@Getter
@NoArgsConstructor
public class GetBandPostUserInfoResponseDto {

    private Long userNo;
    private String username;
    private String profilePath;
}
