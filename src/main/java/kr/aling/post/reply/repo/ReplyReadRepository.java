package kr.aling.post.reply.repo;

import java.util.List;
import kr.aling.post.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Reply 엔티티 조회 전용 데이터 액세스 레이어입니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
public interface ReplyReadRepository extends JpaRepository<Reply, Long> {

    List<Reply> findAllByPostNo(Long postNo);

    List<Reply> findRepliesByPostNoAndIsDeleteIsFalse(Long postNo);
}
