package kr.aling.post.bandpost.service;

import kr.aling.post.bandpost.dto.response.BandPostExceptFileQueryDto;
import kr.aling.post.bandpost.dto.response.BandPostQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
public interface BandPostReadService {

    BandPostQueryDto getBandPostInfo(Long postNo);

    Page<BandPostExceptFileQueryDto> getBandPostsInfoByBand(Long bandNo, Pageable pageable);
}
