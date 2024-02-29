package kr.aling.post.post.service;

import kr.aling.post.bandpost.dto.request.CreateBandPostRequestDto;
import kr.aling.post.post.dto.request.CreatePostRequestDto;
import kr.aling.post.post.dto.request.ModifyPostRequestDto;
import kr.aling.post.post.dto.response.CreatePostResponseDto;

/**
 * 게시물 생성, 수정, 삭제 서비스 레이어.
 *
 * @author : 이성준
 * @since 1.0
 */
public interface PostManageService {

    /**
     * 게시물 생성.
     *
     * @param request : 게시물 생성 데이터
     * @return 게시글 생성 응답 Dto
     */
    CreatePostResponseDto createPost(CreatePostRequestDto request);

    /**
     * 그룹 게시글 생성.
     *
     * @param createBandPostRequestDto 그룹 게시글 생성 요청 Dto
     * @return 게시글 생성 응답 Dto
     */
    CreatePostResponseDto createPost(CreateBandPostRequestDto createBandPostRequestDto);

    /**
     * 게시물 수정.
     *
     * @param postNo  : 수정할 게시물 번호
     * @param request : 게시물 수정에 사용될 데이터
     */
    void modifyPost(Long postNo, ModifyPostRequestDto request);

    /**
     * 게시물 비공개 처리. 게시물 제재 등을 이유로 수정이 아닌 게시물 비공개만 처리.
     *
     * @param postNo 비공개 처리할 게시물 번호
     */
    void privatePost(Long postNo);

    /**
     * 게시물 삭제.
     *
     * @param postNo 삭제할 게시물 번호
     */
    void safeDeleteById(Long postNo);

}
