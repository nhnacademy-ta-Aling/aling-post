package kr.aling.post.reply.service.impl;

import kr.aling.post.common.annotation.ManageService;
import kr.aling.post.common.exception.DeleteContentAccessException;
import kr.aling.post.common.feign.client.UserFeignClient;
import kr.aling.post.common.utils.ReplyUtils;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.exception.PostNotFoundException;
import kr.aling.post.post.repository.PostReadRepository;
import kr.aling.post.reply.dto.request.CreateReplyRequestDto;
import kr.aling.post.reply.dto.request.ModifyReplyRequestDto;
import kr.aling.post.reply.dto.response.CreateReplyResponseDto;
import kr.aling.post.reply.dto.response.ModifyReplyResponseDto;
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
 * @since 1.0
 */
@ManageService
@RequiredArgsConstructor
public class ReplyManageServiceImpl implements ReplyManageService {

    private final ReplyManageRepository replyManageRepository;
    private final ReplyReadRepository replyReadRepository;
    private final PostReadRepository postReadRepository;
    private final UserFeignClient userFeignClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateReplyResponseDto createReply(Long postNo, Long userNo, CreateReplyRequestDto request) {

        Post post = postReadRepository.findById(postNo)
                .orElseThrow(() -> new PostNotFoundException(postNo));

        if (Boolean.TRUE.equals(post.getIsDelete())) {
            throw new DeleteContentAccessException();
        }

        Reply reply = Reply.builder()
                .parentReplyNo(request.getParentReplyNo())
                .userNo(userNo)
                .postNo(postNo)
                .content(request.getContent())
                .build();

        replyManageRepository.save(reply);

        return ReplyUtils.convertToCreateResponse(reply);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModifyReplyResponseDto modifyReply(Long replyNo, Long userNo, ModifyReplyRequestDto request) {
        Reply reply = findReplyEntityByReplyNo(replyNo);
        reply.modifyContent(request.getContent());

        return ReplyUtils.convertToModifyResponse(reply);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void safeDeleteByReplyNo(Long replyNo, Long userNo) {
        Reply reply = findReplyEntityByReplyNo(replyNo);
        reply.softDelete();
    }

    /**
     * 엔티티 조회하는 중복된 코드 라인을 추출한 private 메서드입니다.
     *
     * @param replyNo 조회할 댓글의 번호
     * @return 조회된 댓글 엔티티
     * @since 1.0
     */
    private Reply findReplyEntityByReplyNo(Long replyNo) {
        return replyReadRepository.findById(replyNo).orElseThrow(() -> new ReplyNotFoundException(replyNo));
    }
}
