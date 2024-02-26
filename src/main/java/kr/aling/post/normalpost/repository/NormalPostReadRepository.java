package kr.aling.post.normalpost.repository;

import kr.aling.post.normalpost.dto.response.ReadNormalPostResponseDto;
import kr.aling.post.normalpost.entity.NormalPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 일반 게시물 조회용 데이터 액세스 레이어.
 *
 * @author : 이성준
 * @since 1.0
 */
public interface NormalPostReadRepository extends JpaRepository<NormalPost, Long> {


    /**
     * 유저 번호로 모든 게시물 조회, 페이지네이션 적용.
     *
     * @param userNo 게시글 검색 기준이 될 유저 번호
     * @return 페이지네이션 정보가 포함된 게시물 목록
     * @author : 이성준
     * @since 1.0
     */
    Page<ReadNormalPostResponseDto> findAllByUserNo(Long userNo, Pageable pageable);
}
