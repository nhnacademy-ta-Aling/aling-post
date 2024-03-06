package kr.aling.post.post.service;

import java.util.List;
import kr.aling.post.post.dto.response.IsExistsPostResponseDto;
import kr.aling.post.post.dto.response.ReadPostResponseIntegrationDto;
import kr.aling.post.post.dto.response.ReadPostsForScrapResponseDto;

/**
 * 게시물 조회 서비스 레이어.
 *
 * @author : 이성준
 * @since 1.0
 */

public interface PostReadService {

    /**
     * 게시물 데이터 조회.
     *
     * @param postNo : 조회할 게시물 번호
     * @return : 조회된 게시물의 DTO
     */
    ReadPostResponseIntegrationDto readPostByPostNo(Long postNo);

    /**
     * 게시물 번호로 게시물의 존재 여부를 조회합니다.
     *
     * @param postNo 조회할 게시물의 번호
     * @return 게시물의 존재 여부
     * @author 이수정
     * @since 1.0
     */
    IsExistsPostResponseDto isExistsPost(Long postNo);

    /**
     * 게시물 번호 리스트로 스크랩 조회에 필요한 게시물 내용을 조회해 응답합니다.
     *
     * @param postNos 조회할 게시물 번호 리스트
     * @return 스크랩 조회에 필요한 게시물 내용 리스트를 담은 Dto
     * @author 이수정
     * @since 1.0
     */
    ReadPostsForScrapResponseDto getPostsForScrap(List<Long> postNos);
}
