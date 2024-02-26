package kr.aling.post.reply.controller;

import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.reply.dto.response.ReadReplyResponseDto;
import kr.aling.post.reply.service.ReplyReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 댓글 조회 컨트롤러입니다.
 * accept 헤더와 content-type 헤더를 application/json 를 기본으로 요구합니다.
 *
 * @author : 이성준
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/posts/{postNo}/replies", consumes = {"application/json"}, produces = {
        "application/json"})
@RequiredArgsConstructor
public class ReplyReadController {

    private final ReplyReadService replyReadService;

    /**
     * 게시물 번호로 댓글 목록을 조회합니다.
     * 페이지네이션이 적용됩니다.
     *
     * @param postNo   댓글을 조회할 게시물 번호
     * @param pageable 페이지네이션 정보
     * @return 조회한 댓글의 페이징 응답 객체
     * @author : 이성준
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<PageResponseDto<ReadReplyResponseDto>> readRepliesByPostNo(@PathVariable Long postNo,
                                                                                     Pageable pageable) {

        PageResponseDto<ReadReplyResponseDto> response = replyReadService.readRepliesByPostNo(postNo, pageable);

        return ResponseEntity.ok(response);
    }
}
