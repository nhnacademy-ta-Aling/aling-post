package kr.aling.post.recruitbranchcode.service.impl;

import java.util.List;
import kr.aling.post.common.annotation.ReadService;
import kr.aling.post.recruitbranchcode.dto.response.RecruitBranchCodeGetResponseDto;
import kr.aling.post.recruitbranchcode.repository.RecruitBranchCodeReadRepository;
import kr.aling.post.recruitbranchcode.service.RecruitBranchCodeReadService;
import lombok.RequiredArgsConstructor;

/**
 * 채용 분야 조회를 위한 Service 구현체.
 *
 * @author 정유진
 * @since 1.0
 **/
@ReadService
@RequiredArgsConstructor
public class RecruitBranchCodeReadServiceImpl implements RecruitBranchCodeReadService {

    private final RecruitBranchCodeReadRepository recruitBranchCodeReadRepository;

    @Override
    public List<RecruitBranchCodeGetResponseDto> getRecuitBranchCodeList() {
        return recruitBranchCodeReadRepository.getRecruitBranchCodeAllBy();
    }
}
