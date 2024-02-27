package kr.aling.post.postfile.repository;

import kr.aling.post.postfile.entity.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * PostFile CUD 처리 하는 Repository.
 *
 * @author 박경서
 * @since 1.0
 **/
public interface PostFileManageRepository extends JpaRepository<PostFile, Long> {

}
