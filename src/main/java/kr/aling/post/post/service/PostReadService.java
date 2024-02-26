package kr.aling.post.post.service;

import kr.aling.post.post.dto.response.ReadPostResponseDto;

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
    ReadPostResponseDto readPostByPostNo(Long postNo);

}
