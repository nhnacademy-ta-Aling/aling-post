package kr.aling.post.bandpost.service;

import kr.aling.post.bandpost.dto.response.BandPostExceptFileQueryDto;
import kr.aling.post.bandpost.dto.response.BandPostQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * BandPost Read Service interface.
 *
 * @author 박경서
 * @since 1.0
 **/
public interface BandPostReadService {

    /**
     * 그룹 게시글 단건 조회 메서드.
     *
     * @param postNo 게시글 번호
     * @return 게시글 정보 Query Dto
     */
    BandPostQueryDto getBandPostInfo(Long postNo);

    /**
     * 그룹의 게시글 페이징 조회 메서드.
     *
     * @param bandNo   그룹 번호
     * @param pageable 페이징
     * @return 페이징 게시글 정보 QueryDto
     */
    Page<BandPostExceptFileQueryDto> getBandPostsInfoByBand(Long bandNo, Pageable pageable);
}
