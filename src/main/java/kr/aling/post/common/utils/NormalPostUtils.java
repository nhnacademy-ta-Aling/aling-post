package kr.aling.post.common.utils;

import kr.aling.post.normalpost.dto.request.CreateNormalPostRequestDto;
import kr.aling.post.normalpost.dto.request.ModifyNormalPostRequestDto;
import kr.aling.post.normalpost.dto.response.ReadNormalPostResponseDto;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.post.dto.request.CreatePostRequestDto;
import kr.aling.post.post.dto.request.ModifyPostRequestDto;
import kr.aling.post.post.dto.response.ReadPostResponseDto;
import kr.aling.post.post.entity.Post;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * NormalPost 와 Post 간 요청 및 응답 객체를 변환하는 클래스.
 *
 * @author : 이성준
 * @since 1.0
 * @see kr.aling.post.normalpost.entity.NormalPost
 * @see kr.aling.post.post.entity.Post
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalPostUtils {

    public static ModifyPostRequestDto convert(ModifyNormalPostRequestDto request) {
        return new ModifyPostRequestDto(request.getContent(), request.getIsOpen());
    }

    public static CreatePostRequestDto convert(CreateNormalPostRequestDto request) {
        return new CreatePostRequestDto(request.getContent(), request.getIsOpen());
    }

    public static ReadNormalPostResponseDto convert(NormalPost normalPost) {
        return new ReadNormalPostResponseDto(
                convert(normalPost.getPost()),
                normalPost.getUserNo());
    }

    public static ReadPostResponseDto convert(Post post) {
        return new ReadPostResponseDto(post.getPostNo(), post.getContent(), post.getCreateAt(), post.getModifyAt());
    }
}
