package kr.aling.post.bandposttype.repository;

import kr.aling.post.bandposttype.entity.BandPostType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 그룹 게시글 분류 관리 repository.
 *
 * @author 정유진
 * @since 1.0
 **/
public interface BandPostTypeManageRepository extends JpaRepository<BandPostType, Long> {
}
