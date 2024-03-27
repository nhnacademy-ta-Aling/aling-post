package kr.aling.post.recruitbranchcode.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import kr.aling.post.recruitbranchcode.dto.response.RecruitBranchCodeGetResponseDto;
import kr.aling.post.recruitbranchcode.entity.QRecruitBranchCode;
import kr.aling.post.recruitbranchcode.entity.RecruitBranchCode;
import kr.aling.post.recruitbranchcode.repository.RecruitBranchCodeReadRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * 채용 분야 조회를 위한 Repository 구현체.
 *
 * @author 정유진
 * @since 1.0
 **/
public class RecruitBranchCodeReadRepositoryImpl extends QuerydslRepositorySupport implements
        RecruitBranchCodeReadRepositoryCustom {
    public RecruitBranchCodeReadRepositoryImpl() {
        super(RecruitBranchCode.class);
    }

    /**
     * {@inheritDoc}
     *
     * @return 지역 코드 dto 리스트
     */
    @Override
    public List<RecruitBranchCodeGetResponseDto> getRecruitBranchCodeAllBy() {
        QRecruitBranchCode recruitBranchCode = QRecruitBranchCode.recruitBranchCode;
        return from(recruitBranchCode)
                .select(Projections.constructor(RecruitBranchCodeGetResponseDto.class,
                        recruitBranchCode.recruitBranchCodeNo,
                        recruitBranchCode.name))
                .fetch();
    }
}
