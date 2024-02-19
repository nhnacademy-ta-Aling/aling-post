package kr.aling.post.reply.service;


import kr.aling.post.reply.dto.request.CreateReplyRequestDto;
import kr.aling.post.reply.dto.request.ModifyReplyRequestDto;
import kr.aling.post.reply.dto.response.CreateReplyResponseDto;
import kr.aling.post.reply.dto.response.ModifyReplyResponseDto;

/**
 * 댓글 관리용 서비스 레이어
 *
 * @author : 이성준
 * @since : 1.0
 */
public interface ReplyManageService {

    /**
     * 댓글 작성
     *
     * @param postNo
     * @param request 댓글 작성시 필요한 데이터 객체
     * @return 댓글 작성시 응답 객체
     * @since : 1.0
     */
    CreateReplyResponseDto createReply(Long postNo, CreateReplyRequestDto request);

    /**
     * 댓글 수정
     *
     * @param replyNo 수정할 대상 댓글 번호
     * @param no
     * @param request 댓글 수정시 필요한 데이터 객체
     * @return 댓글 수정시 응답 객체
     * @since : 1.0
     */
    ModifyReplyResponseDto modifyReply(Long replyNo, Long no, ModifyReplyRequestDto request);

    /**
     * 댓글 삭제
     *
     * @param replyNo 삭제 처리할 댓글의 번호
     * @param no
     * @since : 1.0
     */
    void safeDeleteByReplyNo(Long replyNo, Long no);
}
