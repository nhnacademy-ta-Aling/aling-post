package kr.aling.post.reply.repo;

import kr.aling.post.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Reply 엔티티 데이터 관리 전용 데이터 액세스 레이어입니다.
 * 조회 메서드는 암묵적으로 사용하지 않습니다.
 *
 * @author : 이성준
 * @since 1.0
 */
public interface ReplyManageRepository extends JpaRepository<Reply, Long> {

}
