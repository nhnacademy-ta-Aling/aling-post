package kr.aling.post.locationcode.repository;

import kr.aling.post.locationcode.entity.LocationCode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 지역 코드 조회 Repository.
 *
 * @author 정유진
 * @since 1.0
 **/
public interface LocationCodeReadRepository
        extends JpaRepository<LocationCode, String>, LocationCodeReadRepositoryCustom {
}
