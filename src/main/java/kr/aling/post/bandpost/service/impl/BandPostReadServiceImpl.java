package kr.aling.post.bandpost.service.impl;

import kr.aling.post.bandpost.dto.response.BandPostExceptFileQueryDto;
import kr.aling.post.bandpost.dto.response.BandPostQueryDto;
import kr.aling.post.bandpost.exception.BandPostNotFoundException;
import kr.aling.post.bandpost.repository.BandPostReadRepository;
import kr.aling.post.bandpost.service.BandPostReadService;
import kr.aling.post.common.annotation.ReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 그룹 게시글 Read Service 구현체.
 *
 * @author 박경서
 * @since 1.0
 **/
@ReadService
@RequiredArgsConstructor
public class BandPostReadServiceImpl implements BandPostReadService {

    private final BandPostReadRepository bandPostReadRepository;

    /**
     * {@inheritDoc}
     *
     * @param postNo 게시글 번호
     * @return 게시글 정보 Query Dto
     */
    @Override
    public BandPostQueryDto getBandPostInfo(Long postNo) {
        if (!bandPostReadRepository.existsById(postNo)) {
            throw new BandPostNotFoundException();
        }

        return bandPostReadRepository.getBandPostByPostNo(postNo);
    }

    /**
     * {@inheritDoc}
     *
     * @param bandNo   그룹 번호
     * @param pageable 페이징
     * @return 페이징 게시글 정보 QueryDto
     */
    @Override
    public Page<BandPostExceptFileQueryDto> getBandPostsInfoByBand(Long bandNo, Pageable pageable) {
        return bandPostReadRepository.getBandPostByBand(bandNo, pageable);
    }
}
