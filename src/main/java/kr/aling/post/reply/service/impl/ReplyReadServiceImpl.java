package kr.aling.post.reply.service.impl;

import feign.FeignException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import kr.aling.post.common.annotation.ReadService;
import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.common.feign.client.UserFeignClient;
import kr.aling.post.common.utils.PageUtils;
import kr.aling.post.common.utils.ReplyUtils;
import kr.aling.post.post.dto.request.WriterRequestDto;
import kr.aling.post.reply.dto.response.ReadReplyResponseDto;
import kr.aling.post.reply.dto.response.ReadWriterResponseDto;
import kr.aling.post.reply.entity.Reply;
import kr.aling.post.reply.repo.ReplyReadRepository;
import kr.aling.post.reply.service.ReplyReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 댓글 조회 서비스 레이어 구현체입니다.
 *
 * @author : 이성준
 * @since 1.0
 */

@Slf4j
@ReadService
@RequiredArgsConstructor
public class ReplyReadServiceImpl implements ReplyReadService {

    private final ReplyReadRepository replyReadRepository;
    private final UserFeignClient userFeignClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponseDto<ReadReplyResponseDto> readRepliesByPostNo(Long postNo, Pageable pageable) {
        Page<Reply> page = replyReadRepository.findRepliesByPostNoAndIsDeleteIsFalse(postNo, pageable);

        List<Reply> replies = page.getContent();
        Set<Long> users = new HashSet<>();
        replies.forEach(reply -> users.add(reply.getUserNo()));

        List<ReadWriterResponseDto> writers = new ArrayList<>();

        try {
            writers = userFeignClient.requestWriterNames(new WriterRequestDto(users));
        } catch (FeignException e) {
            log.error(e.getMessage());
        }
        Map<Long, String> writerMap = writers.stream().collect(
                Collectors.toMap(ReadWriterResponseDto::getWriterNo, ReadWriterResponseDto::getWriterName)
        );

        List<ReadReplyResponseDto> pageContent = new ArrayList<>();
        replies.forEach(reply -> pageContent.add(ReplyUtils.convertToReadReplyResponse(reply,
                writerMap.getOrDefault(reply.getUserNo(), "Unknown User"))));

        return PageUtils.convert(page, pageContent);
    }
}
