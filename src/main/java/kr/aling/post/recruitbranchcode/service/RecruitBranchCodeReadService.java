package kr.aling.post.recruitbranchcode.service;

import java.util.List;
import kr.aling.post.recruitbranchcode.dto.response.RecruitBranchCodeGetResponseDto;

/**
 * 채용 분야 조회를 위한 Service.
 *
 * @author 정유진
 * @since 1.0
 **/
public interface RecruitBranchCodeReadService {

    List<RecruitBranchCodeGetResponseDto> getRecuitBranchCodeList();
}
