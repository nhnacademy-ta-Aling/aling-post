package kr.aling.post.common.utils;

import kr.aling.post.normalpost.dto.response.ReadNormalPostResponseDto;
import kr.aling.post.normalpost.entity.NormalPost;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * NormalPost 에 대한 객체를 반환하는 유틸리티 클래스.
 *
 * @author : 이성준
 * @since 1.0
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalPostUtils {

    public static ReadNormalPostResponseDto convert(NormalPost normalPost) {
        return new ReadNormalPostResponseDto(
                PostUtils.convert(normalPost.getPost()),
                normalPost.getUserNo());
    }

}
