package kr.aling.post.recruitbranchcode.repository;

import java.util.List;
import kr.aling.post.recruitbranchcode.dto.response.RecruitBranchCodeGetResponseDto;

/**
 * 채용 분야 조회를 위한 custom Repository.
 *
 * @author 정유진
 * @since 1.0
 **/
public interface RecruitBranchCodeReadRepositoryCustom {
    /**
     * 채용 분야 코드 목록 조회를 위한 메서드 입니다.
     *
     * @return 지역 코드 dto 리스트
     */
    List<RecruitBranchCodeGetResponseDto> getRecruitBranchCodeAllBy();
}
