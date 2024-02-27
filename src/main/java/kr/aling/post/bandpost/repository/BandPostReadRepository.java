package kr.aling.post.bandpost.repository;

import kr.aling.post.bandpost.entity.BandPost;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
public interface BandPostReadRepository extends JpaRepository<BandPost, Long>, BandPostReadRepositoryCustom {
}
