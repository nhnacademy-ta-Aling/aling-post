package kr.aling.post.reply.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.common.utils.ReplyUtils;
import kr.aling.post.post.dummy.PostDummy;
import kr.aling.post.post.entity.Post;
import kr.aling.post.reply.dto.response.ReadReplyResponseDto;
import kr.aling.post.reply.dummy.ReplyDummy;
import kr.aling.post.reply.entity.Reply;
import kr.aling.post.reply.repo.ReplyReadRepository;
import kr.aling.post.reply.service.impl.ReplyReadServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 댓글 조회 서비스 레이어 테스트
 *
 * @author : 이성준
 * @since : 1.0
 */

@ExtendWith(SpringExtension.class)
class ReplyReadServiceTest {

    @Mock
    ReplyReadRepository replyReadRepository;

    @InjectMocks
    ReplyReadServiceImpl replyReadService;

    Post post;

    @BeforeEach
    void setUp() {
        post = PostDummy.dummyPost();
        ReflectionTestUtils.setField(post, "postNo", 1L);
    }

    @Test
    @DisplayName("게시물 번호로 댓글 목록 페이지 조회")
    void readRepliesByPostNo() {

        List<ReadReplyResponseDto> content = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Reply reply = ReplyDummy.dummyReply(post.getPostNo());
            ReflectionTestUtils.setField(reply, "replyNo", Integer.toUnsignedLong(i));
            content.add(ReplyUtils.convertToReadResponse(reply));
        }

        Page<ReadReplyResponseDto> page = new PageImpl<>(content);

        given(replyReadRepository.findRepliesByPostNoAndIsDeleteIsFalse(any(), any())).willReturn(page);

        PageResponseDto<ReadReplyResponseDto> actual =
                replyReadService.readRepliesByPostNo(post.getPostNo(), Pageable.unpaged());

        assertAll(
                () -> assertThat(actual.getTotalPages(), not(nullValue())),
                () -> assertThat(actual.getPageNumber(), not(nullValue())),
                () -> assertThat(actual.getTotalElements(), not(nullValue())),
                () -> assertThat(actual.getContent(), not(nullValue()))
        );

        List<ReadReplyResponseDto> actualReplies = actual.getContent();
        assertThat(actualReplies, is(not(empty())));

        actualReplies.forEach(
                reply -> {
                    assertThat(reply.getPostNo(), equalTo(post.getPostNo()));
                }
        );

    }
}