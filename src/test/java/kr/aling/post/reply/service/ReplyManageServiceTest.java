package kr.aling.post.reply.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

import java.util.Optional;
import kr.aling.post.post.dummy.PostDummy;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.exception.PostNotFoundException;
import kr.aling.post.post.repository.PostReadRepository;
import kr.aling.post.reply.dto.request.CreateReplyRequestDto;
import kr.aling.post.reply.dto.request.ModifyReplyRequestDto;
import kr.aling.post.reply.dummy.ReplyDummy;
import kr.aling.post.reply.entity.Reply;
import kr.aling.post.reply.repo.ReplyManageRepository;
import kr.aling.post.reply.repo.ReplyReadRepository;
import kr.aling.post.reply.service.impl.ReplyManageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 댓글 관리 서비스 레이어 테스트입니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@ExtendWith(SpringExtension.class)
class ReplyManageServiceTest {

    @Mock
    ReplyManageRepository replyManageRepository;

    @Mock
    ReplyReadRepository replyReadRepository;

    @Mock
    PostReadRepository postReadRepository;

    @InjectMocks
    ReplyManageServiceImpl replyManageService;

    Reply reply;

    @BeforeEach
    void setUp() {
        reply = ReplyDummy.dummyReply(1L);
    }

    @Test
    @DisplayName("댓글 작성")
    void createReply() {
        Post post = PostDummy.dummyPost();
        CreateReplyRequestDto request =
                new CreateReplyRequestDto(reply.getUserNo(), reply.getPostNo(), reply.getContent());

        given(replyManageRepository.save(any())).willReturn(reply);
        given(postReadRepository.findById(request.getPostNo())).willReturn(Optional.ofNullable(post));

        replyManageService.createReply(null, request);

        then(replyManageRepository).should(times(1)).save(any());
        then(postReadRepository).should(times(1)).findById(request.getPostNo());
    }

    @Test
    @DisplayName("댓글 작성 실패")
    void createReplyFailPostNotFount() {
        CreateReplyRequestDto request =
                new CreateReplyRequestDto(reply.getUserNo(), reply.getPostNo(), reply.getContent());

        doThrow(new PostNotFoundException(request.getPostNo())).when(postReadRepository).findById(request.getPostNo());

        assertThrows(PostNotFoundException.class, () -> replyManageService.createReply(null, request));

        then(postReadRepository).should(times(1)).findById(request.getPostNo());
    }

    @Test
    @DisplayName("대댓글 작성")
    void createReReply() {
        Post post = PostDummy.dummyPost();
        Long parentReplyNo = 999L;

        CreateReplyRequestDto request =
                new CreateReplyRequestDto(reply.getUserNo(), reply.getPostNo(), reply.getContent());

        given(postReadRepository.findById(request.getPostNo())).willReturn(Optional.ofNullable(post));

        replyManageService.createReply(parentReplyNo, request);

        then(replyManageRepository).should(times(1)).save(any());
        then(postReadRepository).should(times(1)).findById(request.getPostNo());
    }


    @Test
    @DisplayName("댓글 수정")
    void modifyReply() {

        String replaceContent = "수정한 댓글 내용";
        ModifyReplyRequestDto request = new ModifyReplyRequestDto(replaceContent);

        given(replyReadRepository.findById(reply.getReplyNo())).willReturn(Optional.ofNullable(reply));

        replyManageService.modifyReply(reply.getReplyNo(), request);

        then(replyReadRepository).should(times(1)).findById(reply.getReplyNo());
        assertThat(reply.getContent(), equalTo(replaceContent));
    }

    @Test
    @DisplayName("댓글 삭제 처리")
    void safeDeleteByReplyNo() {

        given(replyReadRepository.findById(reply.getReplyNo())).willReturn(Optional.ofNullable(reply));

        replyManageService.safeDeleteByReplyNo(reply.getReplyNo());

        then(replyReadRepository).should(times(1)).findById(reply.getReplyNo());
        assertThat(reply.getIsDelete(), is(true));
    }
}