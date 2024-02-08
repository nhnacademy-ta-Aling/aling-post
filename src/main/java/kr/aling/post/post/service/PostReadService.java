package kr.aling.post.post.service;

import kr.aling.post.post.dto.response.ReadPostResponse;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.exception.PostNotFoundException;

/**
 * 게시물 조회 서비스 레이어
 *
 * @author : 이성준
 * @since : 1.0
 */

public interface PostReadService {
    /**
     * 게시물 데이터 조회
     *
     * @param postNo : 조회할 게시물 번호
     * @return : 조회된 게시물의 DTO
     */
    ReadPostResponse readPostByPostNo(Long postNo);

    /**
     * 게시물 엔티티를 가져오는 메서드
     * 수정 등을 이유로 엔티티 반환이 필요한 경우 사용
     *
     * @param postNo : 엔티티를 가져올 게시물 번호
     * @return : 조회된 게시물의 엔티티, 게시물이 존재하지 않을 경우 예외를 던져 Optional 처리하지 않습니다.
     * @throws PostNotFoundException : 파라미터로 주어진 번호에 해당하는 게시물이 존재하지 않을 경우 발생.
     */
    Post findById(Long postNo) throws PostNotFoundException;
}
