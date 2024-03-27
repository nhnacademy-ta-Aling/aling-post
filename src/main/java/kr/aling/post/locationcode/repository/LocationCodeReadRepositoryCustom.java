package kr.aling.post.locationcode.repository;

import java.util.List;
import kr.aling.post.locationcode.dto.response.LocationCodeGetResponseDto;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 지역 코드 조회를 위한 custom repository.
 *
 * @author 정유진
 * @since 1.0
 **/
@NoRepositoryBean
public interface LocationCodeReadRepositoryCustom {

    /**
     * 지역 코드 목록을 조회 하기 위한 쿼리 메서드.
     *
     * @return 지역 코드 정보 dto 리스트
     */
    List<LocationCodeGetResponseDto> getLocationCodeAllBy();
}
