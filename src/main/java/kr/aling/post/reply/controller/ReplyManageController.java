package kr.aling.post.reply.controller;

import java.net.URI;
import javax.validation.Valid;
import kr.aling.post.reply.dto.request.CreateReplyRequestDto;
import kr.aling.post.reply.dto.request.ModifyReplyRequestDto;
import kr.aling.post.reply.dto.response.CreateReplyResponseDto;
import kr.aling.post.reply.dto.response.ModifyReplyResponseDto;
import kr.aling.post.reply.service.ReplyManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 댓글 관리 컨트롤러입니다.
 * accept 헤더와 content-type 헤더를 application/json 를 기본으로 요구합니다.
 *
 * @author : 이성준
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/posts/{postNo}/replies/", consumes = {"application/json"}, produces = {
        "application/json"})
@RequiredArgsConstructor
public class ReplyManageController {

    private final ReplyManageService replyManageService;

    /**
     * 댓글 생성.
     *
     * @param request 댓글 생성시 필요한 데이터 객체
     * @return Created 상태 코드와 생성 응답 객체 바디, 댓글을 작성한 게시물 주소를 Location 헤더를 갖는 ResponseEntity.
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<CreateReplyResponseDto> createReply(@Valid @RequestBody CreateReplyRequestDto request,
                                                              @PathVariable Long postNo) {
        CreateReplyResponseDto response = replyManageService.createReply(postNo, request);

        return ResponseEntity.created(URI.create("/api/v1/post/" + postNo))
                .body(response);
    }

    /**
     * 댓글 수정.
     *
     * @param request 댓글 수정시 필요한 데이터 객체
     * @return OK 상태 코드와 수정에 대한 응답 객체를 바디로 갖는 ResponseEntity.
     * @since 1.0
     */
    @PutMapping("/{replyNo}")
    public ResponseEntity<ModifyReplyResponseDto> modifyReply(@Valid @RequestBody ModifyReplyRequestDto request,
                                                              @PathVariable Long postNo, @PathVariable Long replyNo) {
        ModifyReplyResponseDto modifyReplyResponseDto = replyManageService.modifyReply(postNo, replyNo, request);

        return ResponseEntity.ok(modifyReplyResponseDto);
    }

    /**
     * 댓글 삭제 처리.
     *
     * @param replyNo 삭제 처리할 댓글의 번호
     * @return No Content 상태 코드를 갖는 ResponseEntity
     * @since 1.0
     */
    @DeleteMapping("/{replyNo}")
    public ResponseEntity<Void> safeDeleteByReplyNo(@PathVariable Long postNo, @PathVariable Long replyNo) {
        replyManageService.safeDeleteByReplyNo(postNo, replyNo);

        return ResponseEntity
                .noContent()
                .build();
    }
}
