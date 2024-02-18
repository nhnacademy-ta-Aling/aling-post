package kr.aling.post.reply.service.impl;

import kr.aling.post.common.annotation.ReadService;
import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.common.utils.PageUtils;
import kr.aling.post.reply.dto.response.ReadReplyResponseDto;
import kr.aling.post.reply.repo.ReplyReadRepository;
import kr.aling.post.reply.service.ReplyReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 댓글 조회 서비스 레이어 구현체입니다.
 * 
 * @author : 이성준
 * @since : 1.0
 */
@ReadService
@RequiredArgsConstructor
public class ReplyReadServiceImpl implements ReplyReadService {

    private final ReplyReadRepository replyReadRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponseDto<ReadReplyResponseDto> readRepliesByPostNo(Long postNo, Pageable pageable) {
        Page<ReadReplyResponseDto> response= replyReadRepository.findRepliesByPostNoAndIsDeleteIsFalse(postNo,pageable);

        return PageUtils.convert(response);
    }
}
