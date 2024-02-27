package kr.aling.post.bandpost.repository;

import kr.aling.post.bandpost.entity.BandPost;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BandPost CUD 처리 하는 Repository.
 *
 * @author 박경서
 * @since 1.0
 **/
public interface BandPostManageRepository extends JpaRepository<BandPost, Long> {

}
