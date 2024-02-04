package kr.aling.post.normalpost.repository;

import kr.aling.post.normalpost.entity.NormalPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalPostReadRepository extends JpaRepository<NormalPost, Long> {
    
}
