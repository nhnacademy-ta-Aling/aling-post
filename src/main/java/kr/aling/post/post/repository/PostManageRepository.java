package kr.aling.post.post.repository;

import kr.aling.post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 게시물 생성, 수정, 삭제용 데이터 액세스 레이어
 * 엔티티 조회 메서드인 findById(Object) 은 암묵적으로 사용하지 않습니다.
 *
 * @author : 이성준
 * @since 1.0
 * @see #findById(Object)
 */
public interface PostManageRepository extends JpaRepository<Post, Long> {

}
