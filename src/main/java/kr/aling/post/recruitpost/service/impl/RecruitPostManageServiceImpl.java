package kr.aling.post.recruitpost.service.impl;

import kr.aling.post.common.annotation.ManageService;
import kr.aling.post.common.enums.UserRoleEnum;
import kr.aling.post.locationcode.entity.LocationCode;
import kr.aling.post.locationcode.exception.LocationCodeNotFoundException;
import kr.aling.post.locationcode.repository.LocationCodeReadRepository;
import kr.aling.post.recruitbranchcode.entity.RecruitBranchCode;
import kr.aling.post.recruitbranchcode.exception.RecruitBranchCodeNotFoundException;
import kr.aling.post.recruitbranchcode.repository.RecruitBranchCodeReadRepository;
import kr.aling.post.recruitpost.dto.request.RecruitPostCreateRequestDto;
import kr.aling.post.recruitpost.entity.RecruitPost;
import kr.aling.post.recruitpost.exception.RecruitPostAccessDeniedException;
import kr.aling.post.recruitpost.repository.RecruitPostManageRepository;
import kr.aling.post.recruitpost.service.RecruitPostManageService;
import lombok.RequiredArgsConstructor;

/**
 * 채용 공고 관리를 위한 Service 구현체.
 *
 * @author 정유진
 * @since 1.0
 **/
@ManageService
@RequiredArgsConstructor
public class RecruitPostManageServiceImpl implements RecruitPostManageService {
    private final RecruitPostManageRepository recruitPostManageRepository;
    private final LocationCodeReadRepository locationCodeReadRepository;
    private final RecruitBranchCodeReadRepository recruitBranchCodeReadRepository;

    /**
     * {@inheritDoc}
     *
     * @param createDto 채용 공고 생성에 필요한 정보를 담은 dto
     * @param userNo 회원 번호
     * @param userRole 회원 권한
     */
    @Override
    public void makeRecruitPost(RecruitPostCreateRequestDto createDto, Long userNo, String userRole) {
        // 공통 권한 처리 추가 되면 41~43번 줄 삭제
        if (!userRole.equals(UserRoleEnum.COMPANY.getRoleName())) {
            throw new RecruitPostAccessDeniedException();
        }

        LocationCode locationCode = locationCodeReadRepository.findById(createDto.getLocationCodeNo())
                .orElseThrow(LocationCodeNotFoundException::new);

        RecruitBranchCode recruitBranchCode = recruitBranchCodeReadRepository.findById(createDto.getRecruitBranchCodeNo())
                .orElseThrow(RecruitBranchCodeNotFoundException::new);

        RecruitPost recruitPost = RecruitPost.builder()
                .userNo(userNo)
                .locationCode(locationCode)
                .recruitBranchCode(recruitBranchCode)
                .title(createDto.getTitle())
                .content(createDto.getContent())
                .salary(createDto.getSalary())
                .careerYear(createDto.getCareerYear())
                .startAt(createDto.getStartAt())
                .endAt(createDto.getEndAt())
                .isOpen(createDto.getIsOpen())
                .build();

        recruitPostManageRepository.save(recruitPost);
    }
}
