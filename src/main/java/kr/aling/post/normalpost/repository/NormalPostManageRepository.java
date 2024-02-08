package kr.aling.post.normalpost.repository;

import kr.aling.post.normalpost.entity.NormalPost;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 일반 게시물 생성, 수정, 삭제용 데이터 액세스 레이어
 *
 * @author : 이성준
 * @since : 1.0
 */
public interface NormalPostManageRepository extends JpaRepository<NormalPost, Long> {
    
}
