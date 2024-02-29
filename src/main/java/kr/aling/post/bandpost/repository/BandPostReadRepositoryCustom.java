package kr.aling.post.bandpost.repository;

import kr.aling.post.bandpost.dto.response.BandPostExceptFileQueryDto;
import kr.aling.post.bandpost.dto.response.BandPostQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * BandPost QueryDsl Custom Repository.
 *
 * @author 박경서
 * @since 1.0
 **/
@NoRepositoryBean
public interface BandPostReadRepositoryCustom {

    /**
     * 게시글 정보 단건 조회.
     *
     * @param postNo 게시글 번호
     * @return 게시글 정보 Query Dto
     */
    BandPostQueryDto getBandPostByPostNo(Long postNo);

    /**
     * 그룹의 게시글 페이징 조회 (작성 최신 순서 정렬).
     *
     * @param bandNo   그룹 번호
     * @param pageable 페이징
     * @return 페이징 된 게시글 정보 QueryDto
     */
    Page<BandPostExceptFileQueryDto> getBandPostByBand(Long bandNo, Pageable pageable);
}
