package kr.aling.post.recruitbranchcode.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 채용 분야 조회를 위한 dto.
 *
 * @author 정유진
 * @since 1.0
 **/
@AllArgsConstructor
@Getter
public class RecruitBranchCodeGetResponseDto {
    private String branchCodeNo;
    private String branchName;
}
