package kr.aling.post.postfile.repository;

import kr.aling.post.postfile.entity.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
public interface PostFileReadRepository extends JpaRepository<PostFile, Long>, PostFileReadRepositoryCustom {
}
