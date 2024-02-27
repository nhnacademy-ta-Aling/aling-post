package kr.aling.post.bandpost.repository;

import kr.aling.post.bandpost.dto.response.BandPostExceptFileQueryDto;
import kr.aling.post.bandpost.dto.response.BandPostQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
@NoRepositoryBean
public interface BandPostReadRepositoryCustom {

    BandPostQueryDto getBandPostByPostNo(Long postNo);

    Page<BandPostExceptFileQueryDto> getBandPostByBand(Long bandNo, Pageable pageable);
}
