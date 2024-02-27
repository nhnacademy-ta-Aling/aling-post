package kr.aling.post.bandpost.service.impl;

import kr.aling.post.bandpost.dto.response.BandPostExceptFileQueryDto;
import kr.aling.post.bandpost.dto.response.BandPostQueryDto;
import kr.aling.post.bandpost.repository.BandPostReadRepository;
import kr.aling.post.bandpost.service.BandPostReadService;
import kr.aling.post.common.annotation.ReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
@ReadService
@RequiredArgsConstructor
public class BandPostReadServiceImpl implements BandPostReadService {

    private final BandPostReadRepository bandPostReadRepository;

    @Override
    public BandPostQueryDto getBandPostInfo(Long postNo) {
        return bandPostReadRepository.getBandPostByPostNo(postNo);
    }

    @Override
    public Page<BandPostExceptFileQueryDto> getBandPostsInfoByBand(Long bandNo, Pageable pageable) {
        return bandPostReadRepository.getBandPostByBand(bandNo, pageable);
    }
}
