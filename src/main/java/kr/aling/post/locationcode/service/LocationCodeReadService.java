package kr.aling.post.locationcode.service;

import java.util.List;
import kr.aling.post.locationcode.dto.response.LocationCodeGetResponseDto;

/**
 * 지역 코드 조회를 위한 Service.
 *
 * @author 정유진
 * @since 1.0
 **/
public interface LocationCodeReadService {

    /**
     * 지역 코드 목록 조회를 위한 메서드.
     *
     * @return 지역 코드 정보 dto 리스트
     */
    List<LocationCodeGetResponseDto> getLocationCodeList();

}
