package kr.aling.post.recruitpost.controller;

import java.util.List;
import kr.aling.post.recruitbranchcode.dto.response.RecruitBranchCodeGetResponseDto;
import kr.aling.post.recruitbranchcode.service.RecruitBranchCodeReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 채용 분야 조회를 위한 Controller.
 *
 * @author 정유진
 * @since 1.0
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recruit-posts")
public class RecruitPostReadController {

    private final RecruitBranchCodeReadService recruitBranchCodeReadService;

    /**
     * 채용 분야 리스트 조회를 위한 메서드 입니다.
     *
     * @return 200 ok. 채용 분야 리스트
     */
    @GetMapping
    public ResponseEntity<List<RecruitBranchCodeGetResponseDto>> recruitBranchCodeList() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recruitBranchCodeReadService.getRecuitBranchCodeList());
    }
}
