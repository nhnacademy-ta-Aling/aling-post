package kr.aling.post.common.utils;

import kr.aling.post.post.dto.response.ReadPostResponseDto;
import kr.aling.post.post.entity.Post;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author : 이성준
 * @since : 1.0
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUtils {
    public static ReadPostResponseDto convert(Post post) {
        return ReadPostResponseDto.builder()
                .postNo(post.getPostNo())
                .content(post.getContent())
                .createAt(post.getCreateAt())
                .modifyAt(post.getModifyAt())
                .build();
    }
}
