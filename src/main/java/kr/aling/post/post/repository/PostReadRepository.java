package kr.aling.post.post.repository;

import java.util.Optional;
import kr.aling.post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 일반 게시물 조회용 데이터 액세스 레이어.
 *
 * @author : 이성준
 * @since 1.0
 */
public interface PostReadRepository extends JpaRepository<Post, Long>, PostReadRepositoryCustom {

    Optional<Post> findByPostNoAndIsDeleteFalse(Long postNo);
}
