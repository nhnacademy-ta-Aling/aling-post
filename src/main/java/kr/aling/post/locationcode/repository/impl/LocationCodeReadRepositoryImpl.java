package kr.aling.post.locationcode.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import kr.aling.post.locationcode.dto.response.LocationCodeGetResponseDto;
import kr.aling.post.locationcode.entity.LocationCode;
import kr.aling.post.locationcode.entity.QLocationCode;
import kr.aling.post.locationcode.repository.LocationCodeReadRepository;
import kr.aling.post.locationcode.repository.LocationCodeReadRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * 지역 코드 조회를 위한 repository 구현체.
 *
 * @author 정유진
 * @since 1.0
 **/
public class LocationCodeReadRepositoryImpl extends QuerydslRepositorySupport implements
        LocationCodeReadRepositoryCustom {

    public LocationCodeReadRepositoryImpl() {
        super(LocationCode.class);
    }

    /**
     * {@inheritDoc}
     *
     * @return 지역 코드 정보 dto 리스트
     */
    @Override
    public List<LocationCodeGetResponseDto> getLocationCodeAllBy() {
        QLocationCode locationCode = QLocationCode.locationCode;

        return from(locationCode)
                .select(Projections.constructor(LocationCodeGetResponseDto.class,
                        locationCode.locationCodeNo,
                        locationCode.name))
                .fetch();
    }
}
