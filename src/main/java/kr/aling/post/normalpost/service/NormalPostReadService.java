package kr.aling.post.normalpost.service;

import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.normalpost.dto.response.ReadNormalPostResponseDto;
import org.springframework.data.domain.Pageable;

/**
 * 일반 게시물 조회 서비스 레이어.
 *
 * @author : 이성준
 * @since 1.0
 */
public interface NormalPostReadService {

    /**
     * 게시물 번호를 조건으로 일반 게시물을 조회하는 메서드.
     *
     * @param postNo 조회할 일반 게시물의 번호
     * @return 조회된 일반 게시물의 DTO
     */
    ReadNormalPostResponseDto readNormalPostByPostNo(Long postNo);

    /**
     * 유저 번호를 기준으로 페이징이 된 게시물 목록을 찾는 메서드.
     *
     * @param userNo 게시물을 불러올 기준 유저 정보
     * @param pageable 게시물 페이지네이션에 필요한 정보
     * @return 페이지네이션이 적용된 일반 게시물 조회 DTO 목록
     */
    PageResponseDto<ReadNormalPostResponseDto> readNormalPostsByUserNo(long userNo, Pageable pageable);
}
