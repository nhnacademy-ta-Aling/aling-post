package kr.aling.post.normalpost.service;

import kr.aling.post.normalpost.dto.response.ReadNormalPostResponse;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.post.exception.PostNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 일반 게시물 조회 서비스 레이어.
 *
 * @author : 이성준
 * @since : 1.0
 */
public interface NormalPostReadService {

    /**
     * 일반 게시물 조회
     *
     * @param postNo : 조회할 일반 게시물
     * @return 조회된 일반 게시물의 DTO
     */
    ReadNormalPostResponse readNormalPostByPostNo(Long postNo);


    /**
     * 일반 게시물 엔티티 반환이 필요한 경우 사용.
     *
     * @param postNo 파일 번호
     * @return 일반 게시물 엔티티.
     * @throws PostNotFoundException 주어진 번호의 일반 게시물을 찾지 못했을 때 던져지는 예외
     */
    NormalPost findById(Long postNo) throws PostNotFoundException;

    /**
     * 유저 번호를 기준으로 페이지네이션 정보가 포함된 응답이 필요할 때 사용.
     *
     * @param userNo 게시물을 불러올 기준 유저 정보
     * @param pageable 게시물 페이지네이션에 필요한 정보
     * @return 페이지네이션이 적용된 일반 게시물 조회 DTO 목록
     */
    Page<ReadNormalPostResponse> readNormalPostsByUserNo(long userNo, Pageable pageable);
}
