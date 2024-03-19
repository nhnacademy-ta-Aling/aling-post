package kr.aling.post.reply.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kr.aling.post.common.annotation.ReadService;
import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.common.utils.PageUtils;
import kr.aling.post.common.utils.ReplyUtils;
import kr.aling.post.post.dto.request.ReadAuthorInfoRequestDto;
import kr.aling.post.reply.dto.response.ReadReplyDetailResponseDto;
import kr.aling.post.reply.dto.response.ReadUserInfoResponseDto;
import kr.aling.post.reply.entity.Reply;
import kr.aling.post.reply.repo.ReplyReadRepository;
import kr.aling.post.reply.service.ReplyReadService;
import kr.aling.post.user.adaptor.AuthorInformationAdaptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

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
    private final AuthorInformationAdaptor authorInformationAdaptor;

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponseDto<ReadReplyDetailResponseDto> readRepliesByPostNo(Long postNo, Pageable pageable) {
        Page<Reply> page = replyReadRepository.findRepliesByPostNoAndIsDeleteIsFalse(postNo, pageable);
        List<Reply> replies = page.getContent();

        Set<ReadAuthorInfoRequestDto> users = getUsers(replies);

        Map<Long, ReadUserInfoResponseDto> authorInfoMap = authorInformationAdaptor.readReplyAuthorInfo(users);

        List<ReadReplyDetailResponseDto> pageContent = setAuthorInfo(replies, authorInfoMap);

        return PageUtils.convert(
                PageableExecutionUtils.getPage(pageContent, page.getPageable(), page.getPageable()::getOffset));
    }

    /**
     * 댓글 목록에서 사용자 식별 정보를 추출하는 private 메서드입니다.
     *
     * @param replies 사용자 식별 정보를 추출한 댓글 목록
     * @return 추출된 사용자 식별 정보
     * @author : 이성준
     * @since : 1.0
     */
    private Set<ReadAuthorInfoRequestDto> getUsers(List<Reply> replies) {
        Set<ReadAuthorInfoRequestDto> users = new HashSet<>();
        replies.forEach(reply -> users.add(new ReadAuthorInfoRequestDto(reply.getUserNo())));

        return users;
    }

    /**
     * 댓글 목록에 대응하여 작성자 정보를 매치시키는 메서드입니다.
     *
     * @param replies       기반이 되는 댓글 목록
     * @param authorInfoMap 작성자 식별 정보를 key 로 사용하는 유저 정보 객체의 맵
     * @return
     * @author : 이성준
     * @since : 1.0
     */
    private List<ReadReplyDetailResponseDto> setAuthorInfo(List<Reply> replies,
            Map<Long, ReadUserInfoResponseDto> authorInfoMap) {
        List<ReadReplyDetailResponseDto> pageContent = new ArrayList<>();

        replies.forEach(reply -> pageContent.add(
                new ReadReplyDetailResponseDto(
                        ReplyUtils.convertToReadReplyResponse(reply),
                        authorInfoMap.get(reply.getUserNo()))));

        return pageContent;
    }
}
