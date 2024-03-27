package kr.aling.post.recruitbranchcode.repository;

import kr.aling.post.recruitbranchcode.entity.RecruitBranchCode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 채용 분야 조회를 위한 Repository.
 *
 * @author 정유진
 * @since 1.0
 **/
public interface RecruitBranchCodeReadRepository
        extends JpaRepository<RecruitBranchCode, String>, RecruitBranchCodeReadRepositoryCustom {
}
