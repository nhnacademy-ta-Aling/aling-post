package kr.aling.post.common.utils;

import kr.aling.post.normalpost.dto.request.CreateNormalPostRequestDto;
import kr.aling.post.normalpost.dto.request.ModifyNormalPostRequestDto;
import kr.aling.post.post.dto.request.CreatePostRequestDto;
import kr.aling.post.post.dto.request.ModifyPostRequestDto;
import kr.aling.post.post.dto.response.ReadPostResponseDto;
import kr.aling.post.post.entity.Post;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Post 에 대한 객체를 반환하는 유틸리티 클래스
 *
 * @author : 이성준
 * @since : 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUtils {

    /**
     * 게시물 엔티티를 조회 응답 객체로 변환합니다.
     *
     * @param post 변환할 게시물 엔티티
     * @return 변환된 응답 객체
     * @author : 이성준
     * @since : 1.0
     */
    public static ReadPostResponseDto convert(Post post) {
        return ReadPostResponseDto.builder()
                .postNo(post.getPostNo())
                .content(post.getContent())
                .createAt(post.getCreateAt())
                .modifyAt(post.getModifyAt())
                .build();
    }

    /**
     * 일반 게시물 엔티티에 대한 수정 객체를 게시물 엔티티로 변환합니다.
     *
     * @param request 변환할 수정 요청 객체
     * @return 게시물에 대한 수정 요청 객체
     * @author : 이성준
     * @since : 1.0
     */
    public static ModifyPostRequestDto convert(ModifyNormalPostRequestDto request) {
        return new ModifyPostRequestDto(request.getContent(), request.getIsOpen());
    }

    /**
     * 일반 게시물 엔티티에 대한 작성 객체를 게시물 엔티티로 변환합니다.
     *
     * @param request 변환할 작성 요청 객체
     * @return 게시물에 대한 작성 요청 객체
     * @author : 이성준
     * @since : 1.0
     */
    public static CreatePostRequestDto convert(CreateNormalPostRequestDto request) {
        return new CreatePostRequestDto(request.getContent(), request.getIsOpen());
    }
}
