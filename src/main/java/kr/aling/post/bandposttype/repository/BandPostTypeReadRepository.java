package kr.aling.post.bandposttype.repository;

import kr.aling.post.bandposttype.entity.BandPostType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 그룹 게시글 분류 조회용 Repository.
 *
 * @author 박경서
 * @since 1.0
 **/
public interface BandPostTypeReadRepository
        extends JpaRepository<BandPostType, Long>, BandPostTypeReadRepositoryCustom {

}
