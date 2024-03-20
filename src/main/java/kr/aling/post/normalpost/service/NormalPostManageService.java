package kr.aling.post.normalpost.service;


import kr.aling.post.normalpost.dto.request.CreateNormalPostRequestDto;
import kr.aling.post.normalpost.dto.request.ModifyNormalPostRequestDto;
import kr.aling.post.normalpost.dto.response.CreateNormalPostResponseDto;

/**
 * 일반 게시물 생성, 수정, 삭제 서비스 레이어.
 *
 * @author : 이성준
 * @since 1.0
 */
public interface NormalPostManageService {

    /**
     * 일반 게시물 생성.
     *
     * @param userNo  게시물 작성을 요청한 유저 번호
     * @param request 게시물 작성 요청에 대한 폼 데이터
     * @return 생성된 게시물의 번호
     */
    CreateNormalPostResponseDto createNormalPost(Long userNo, CreateNormalPostRequestDto request);

    /**
     * 일반 게시물 수정.
     *
     * @param postNo  수정 요청한 게시물의 번호
     * @param userNo
     * @param request 게시물 수정 요청에 대한 폼 데이터
     */
    void modifyNormalPost(Long postNo, Long userNo, ModifyNormalPostRequestDto request);

    /**
     * 일반 게시물 삭제.
     *
     * @param postNo 삭제하려는 게시물 번호
     */
    void softDeleteById(Long postNo, Long userNo);
}
