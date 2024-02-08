package kr.aling.post.normalpost.controller;

import kr.aling.post.normalpost.dto.response.ReadNormalPostResponse;
import kr.aling.post.normalpost.service.NormalPostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 일반 게시물 조회 컨트롤러 입니다.
 *
 * @since : 1.0
 */
@RequestMapping("/api/v1/normal-posts")
@RequiredArgsConstructor
@RestController
public class NormalPostReadController {

    private final NormalPostReadService normalPostReadService;

    /**
     * 일반 게시물 조회 처리
     *
     * @param postNo 조회할 일반 게시물의 번호
     * @return 조회된 게시물의 데이터와 성공 여부
     */
    @GetMapping("/{postNo}")
    public ResponseEntity<ReadNormalPostResponse> readNormalPost(@PathVariable long postNo) {

        ReadNormalPostResponse response = normalPostReadService.readNormalPostByPostNo(postNo);

        return ResponseEntity.ok()
                .body(response);
    }


    /**
     * 유저 번호를 기준으로 게시물 목록 조회
     *
     * @param userNo   조회할 게시물들의 기준 회원 번호
     * @param pageable 페이지네이션 정보
     * @return 조회된 게시물의 데이터와 성공 여부
     */
    @GetMapping
    public ResponseEntity<Page<ReadNormalPostResponse>> readNormalPostsByUser(@RequestParam long userNo,
                                                                              Pageable pageable) {
        Page<ReadNormalPostResponse> responses = normalPostReadService.readNormalPostsByUserNo(userNo, pageable);

        return ResponseEntity.ok()
                .body(responses);
    }
}
