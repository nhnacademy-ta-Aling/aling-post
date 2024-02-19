package kr.aling.post.bandpost.service;

import kr.aling.post.bandpost.dto.request.CreateBandPostRequestDto;
import kr.aling.post.post.dto.response.CreatePostResponseDtoTmp;

/**
 * 그룹 게시글 생성, 수정, 삭제 Service Interface.
 *
 * @author 박경서
 * @since 1.0
 **/
public interface BandPostManageService {

    /**
     * 그룹 게시글 생성.
     *
     * @param createPostResponseDtoTmp 게시글 생성 응답 Dto
     * @param createBandPostRequestDto 그룹 게시글 생성 요청 Dto
     * @param baneUserNo               그룹 회원 번호
     */
    void createBandPost(CreatePostResponseDtoTmp createPostResponseDtoTmp,
                        CreateBandPostRequestDto createBandPostRequestDto, Long baneUserNo);
}
