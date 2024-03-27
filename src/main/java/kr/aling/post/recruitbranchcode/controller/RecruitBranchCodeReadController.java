package kr.aling.post.recruitbranchcode.controller;

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
 * Some description here.
 *
 * @author 정유진
 * @since 1.0
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recruit-branch-codes")
public class RecruitBranchCodeReadController {
    private final RecruitBranchCodeReadService recruitBranchCodeReadService;
    @GetMapping
    public ResponseEntity<List<RecruitBranchCodeGetResponseDto>> recruitBranchCodeList() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recruitBranchCodeReadService.getRecuitBranchCodeList());
    }
}
