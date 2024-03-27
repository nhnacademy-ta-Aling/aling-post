package kr.aling.post.recruitpost.service;

import kr.aling.post.recruitpost.dto.request.RecruitPostCreateRequestDto;

/**
 * 채용 공고 관리를 위한 Service.
 *
 * @author 정유진
 * @since 1.0
 **/
public interface RecruitPostManageService {

    /**
     * 채용 공고 생성을 위한 메서드 입니다.
     *
     * @param createDto 채용 공고 생성에 필요한 정보를 담은 dto
     * @param userNo 회원 번호
     * @param userRole 회원 권한
     */
    void makeRecruitPost(RecruitPostCreateRequestDto createDto, Long userNo, String userRole);
}
