package kr.aling.post.reply.service.impl;

import kr.aling.post.common.annotation.ManageService;
import kr.aling.post.common.exception.DeleteContentAccessException;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.exception.PostNotFoundException;
import kr.aling.post.post.repository.PostReadRepository;
import kr.aling.post.reply.dto.request.CreateReplyRequestDto;
import kr.aling.post.reply.dto.request.ModifyReplyRequestDto;
import kr.aling.post.reply.entity.Reply;
import kr.aling.post.reply.exception.ReplyNotFoundException;
import kr.aling.post.reply.repo.ReplyManageRepository;
import kr.aling.post.reply.repo.ReplyReadRepository;
import kr.aling.post.reply.service.ReplyManageService;
import lombok.RequiredArgsConstructor;

/**
 * 댓글 관리 서비스 레이어 구현체입니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@ManageService
@RequiredArgsConstructor
public class ReplyManageServiceImpl implements ReplyManageService {

    private final ReplyManageRepository replyManageRepository;
    private final ReplyReadRepository replyReadRepository;
    private final PostReadRepository postReadRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createReply(Long parentReplyNo, CreateReplyRequestDto request) {

        Post post = postReadRepository.findById(request.getPostNo())
                .orElseThrow(() -> new PostNotFoundException(request.getPostNo()));

        if (Boolean.TRUE.equals(post.getIsDelete())) {
            throw new DeleteContentAccessException();
        }

        Reply reply = Reply.builder()
                .parentReplyNo(parentReplyNo)
                .userNo(request.getUserNo())
                .postNo(request.getPostNo())
                .content(request.getContent())
                .build();

        replyManageRepository.save(reply);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyReply(Long replyNo, ModifyReplyRequestDto request) {
        Reply reply = findReplyEntityByReplyNo(replyNo);
        reply.modifyContent(request.getContent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void safeDeleteByReplyNo(Long replyNo) {
        Reply reply = findReplyEntityByReplyNo(replyNo);
        reply.softDelete();
    }

    /**
     * 엔티티 조회하는 중복된 코드 라인을 추출한 private 메서드입니다.
     *
     * @param replyNo 조회할 댓글의 번호
     * @return 조회된 댓글 엔티티
     * @since : 1.0
     */
    private Reply findReplyEntityByReplyNo(Long replyNo) {
        return replyReadRepository.findById(replyNo).orElseThrow(() -> new ReplyNotFoundException(replyNo));
    }
}
