package kr.aling.post.recruitpost.repository;

import kr.aling.post.recruitpost.entity.RecruitPost;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 채용 공고 조회를 위한 Repository.
 *
 * @author 정유진
 * @since 1.0
 **/
public interface RecruitPostReadRepository extends JpaRepository<RecruitPost, Long> {
}
