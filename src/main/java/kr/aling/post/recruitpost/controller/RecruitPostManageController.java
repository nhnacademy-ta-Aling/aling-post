package kr.aling.post.recruitpost.controller;

import javax.validation.Valid;
import kr.aling.post.common.utils.ConstantUtil;
import kr.aling.post.recruitpost.dto.request.RecruitPostCreateRequestDto;
import kr.aling.post.recruitpost.service.RecruitPostManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Some description here.
 *
 * @author 정유진
 * @since 1.0
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recruit-posts")
public class RecruitPostManageController {
    private final RecruitPostManageService recruitPostManageService;

    @PostMapping
    public void makeRecruitPost(@Valid @RequestBody RecruitPostCreateRequestDto createDto,
                                @RequestHeader(ConstantUtil.X_USER_NO) Long userNo,
                                @RequestHeader(ConstantUtil.X_USER_ROLE) String userRole) {
        recruitPostManageService.makeRecruitPost(createDto, userNo, userRole);
    }

}
