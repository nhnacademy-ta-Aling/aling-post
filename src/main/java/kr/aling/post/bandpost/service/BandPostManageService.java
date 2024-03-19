package kr.aling.post.bandpost.service;

import kr.aling.post.bandpost.dto.request.CreateBandPostRequestDto;
import kr.aling.post.bandpost.dto.request.ModifyBandPostRequestDto;
import kr.aling.post.post.dto.response.CreatePostResponseDto;

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
     * @param createPostResponseDto    게시글 생성 응답 Dto
     * @param createBandPostRequestDto 그룹 게시글 생성 요청 Dto
     * @param baneUserNo               그룹 회원 번호
     * @param alingUserNo              회원 번호
     */
    void createBandPost(CreatePostResponseDto createPostResponseDto,
                        CreateBandPostRequestDto createBandPostRequestDto, Long baneUserNo, Long alingUserNo);

    /**
     * 그룹 게시글 수정.
     *
     * @param postNo                   게시글 번호
     * @param modifyBandPostRequestDto 그룹 게시글 수정 요청 Dto
     */
    void modifyBandPost(Long postNo, ModifyBandPostRequestDto modifyBandPostRequestDto);
}
