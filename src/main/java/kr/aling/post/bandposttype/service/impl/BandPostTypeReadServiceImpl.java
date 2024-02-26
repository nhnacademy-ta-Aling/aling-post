package kr.aling.post.bandposttype.service.impl;

import java.util.List;
import kr.aling.post.bandposttype.dto.response.GetBandPostTypeResponseDto;
import kr.aling.post.bandposttype.repository.BandPostTypeReadRepository;
import kr.aling.post.bandposttype.service.BandPostTypeReadService;
import kr.aling.post.common.annotation.ReadService;
import lombok.RequiredArgsConstructor;

/**
 * 그룹 게시글 분류 조회 service 구현체.
 *
 * @author 정유진
 * @since 1.0
 **/
@ReadService
@RequiredArgsConstructor
public class BandPostTypeReadServiceImpl implements BandPostTypeReadService {
    private final BandPostTypeReadRepository bandPostTypeReadRepository;

    /**
     * {@inheritDoc}
     *
     * @param bandNo 그룹 번호
     * @return 그룹 게시글 분류 dto 리스트
     */
    @Override
    public List<GetBandPostTypeResponseDto> getBandPostTypeList(Long bandNo) {
        return bandPostTypeReadRepository.getBandPostTypeListByBandNo(bandNo);
    }
}
