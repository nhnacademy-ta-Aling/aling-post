package kr.aling.post.reply.service;


import kr.aling.post.reply.dto.request.CreateReplyRequestDto;
import kr.aling.post.reply.dto.request.ModifyReplyRequestDto;

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
     * @param parentReplyNo 작성하려는 댓글의 부모 댓글, nullable
     * @param request 댓글 작성시 필요한 데이터 객체
     * @author : 이성준
     * @since : 1.0
     */
    void createReply(Long parentReplyNo, CreateReplyRequestDto request);

    /**
     * 댓글 수정
     *
     * @param replyNo 수정할 댓글의 번호
     * @param request 댓글 수정시 필요한 데이터 객체
     * @since : 1.0
     */
    void modifyReply(Long replyNo, ModifyReplyRequestDto request);

    /**
     * 댓글 삭제 처리
     *
     * @param replyNo 삭제 처리할 댓글의 번호
     * @author : 이성준
     * @since : 1.0
     */
    void safeDeleteByReplyNo(Long replyNo);
}
