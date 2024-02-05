package kr.aling.post.normalpost.repository;

import java.util.List;
import kr.aling.post.normalpost.dto.response.ReadNormalPostResponse;
import kr.aling.post.normalpost.entity.NormalPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalPostReadRepository extends JpaRepository<NormalPost, Long> {

    List<ReadNormalPostResponse> findAllByUserNo(long userNo);
}
