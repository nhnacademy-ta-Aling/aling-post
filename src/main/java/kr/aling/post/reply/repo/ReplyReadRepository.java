package kr.aling.post.reply.repo;

import kr.aling.post.reply.dto.response.ReadReplyResponseDto;
import kr.aling.post.reply.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Reply 엔티티 조회 전용 데이터 액세스 레이어입니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
public interface ReplyReadRepository extends JpaRepository<Reply, Long> {

    Page<ReadReplyResponseDto> findRepliesByPostNoAndIsDeleteIsFalse(Long postNo, Pageable pageable);
}
