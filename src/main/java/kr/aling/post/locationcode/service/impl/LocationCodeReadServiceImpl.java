package kr.aling.post.locationcode.service.impl;

import java.util.List;
import kr.aling.post.common.annotation.ReadService;
import kr.aling.post.locationcode.dto.response.LocationCodeGetResponseDto;
import kr.aling.post.locationcode.repository.LocationCodeReadRepository;
import kr.aling.post.locationcode.service.LocationCodeReadService;
import lombok.RequiredArgsConstructor;

/**
 * 지역 코드 조회를 위한 Service 구현체.
 *
 * @author 정유진
 * @since 1.0
 **/
@ReadService
@RequiredArgsConstructor
public class LocationCodeReadServiceImpl implements LocationCodeReadService {
    private final LocationCodeReadRepository locationCodeReadRepository;

    /**
     * {@inheritDoc}
     *
     * @return 지역 코드 정보 dto 리스트
     */
    @Override
    public List<LocationCodeGetResponseDto> getLocationCodeList() {
        return locationCodeReadRepository.getLocationCodeAllBy();
    }
}
