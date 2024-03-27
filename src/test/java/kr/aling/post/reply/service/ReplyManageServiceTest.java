package kr.aling.post.reply.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

import java.util.Optional;
import kr.aling.post.common.feign.client.UserFeignClient;
import kr.aling.post.post.dummy.PostDummy;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.exception.PostNotFoundException;
import kr.aling.post.post.repository.PostReadRepository;
import kr.aling.post.reply.dto.request.CreateReplyRequestDto;
import kr.aling.post.reply.dto.request.ModifyReplyRequestDto;
import kr.aling.post.reply.dto.response.CreateReplyResponseDto;
import kr.aling.post.reply.dto.response.ModifyReplyResponseDto;
import kr.aling.post.reply.dummy.ReplyDummy;
import kr.aling.post.reply.entity.Reply;
import kr.aling.post.reply.repo.ReplyManageRepository;
import kr.aling.post.reply.repo.ReplyReadRepository;
import kr.aling.post.reply.service.impl.ReplyManageServiceImpl;
import kr.aling.post.user.dto.response.IsExistsUserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

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

    @Mock
    UserFeignClient userFeignClient;

    @InjectMocks
    ReplyManageServiceImpl replyManageService;

    Reply reply;

    @BeforeEach
    void setUp() {
        reply = ReplyDummy.dummyReply(1L);

        given(userFeignClient.isExistUser(reply.getUserNo())).willReturn(new IsExistsUserResponseDto() {
            @Override
            public Boolean getIsExists() {
                return true;
            }
        });
    }

    @Test
    @DisplayName("댓글 작성")
    void createReply() {
        Post post = PostDummy.postDummy();
        Long userNo = 1L;
        CreateReplyRequestDto request = ReplyDummy.dummyCreateRequest();

        given(replyManageRepository.save(any())).willReturn(reply);
        given(postReadRepository.findById(reply.getPostNo())).willReturn(Optional.ofNullable(post));

        CreateReplyResponseDto actual = replyManageService.createReply(reply.getPostNo(), userNo, request);

        then(replyManageRepository).should(times(1)).save(any());
        then(postReadRepository).should(times(1)).findById(reply.getPostNo());

        assertThat(actual.getParentReplyNo(), is(nullValue()));
        assertThat(actual.getContent(), equalTo(request.getContent()));
    }

    @Test
    @DisplayName("댓글 작성")
    void createReReply() {
        Post post = PostDummy.postDummy();
        Long userNo = 1L;
        Long parentReplyNo = 999L;

        CreateReplyRequestDto request = ReplyDummy.dummyCreateRequest();

        ReflectionTestUtils.setField(request, "parentReplyNo", parentReplyNo);

        given(postReadRepository.findById(reply.getPostNo())).willReturn(Optional.ofNullable(post));

        CreateReplyResponseDto actual = replyManageService.createReply(reply.getPostNo(), userNo, request);

        then(replyManageRepository).should(times(1)).save(any());
        then(postReadRepository).should(times(1)).findById(reply.getPostNo());

        assertThat(actual.getParentReplyNo(), is(not(nullValue())));
        assertThat(actual.getContent(), equalTo(request.getContent()));
    }

    @Test
    @DisplayName("댓글 작성 실패")
    void createReplyFailPostNotFount() {
        CreateReplyRequestDto request = ReplyDummy.dummyCreateRequest();
        Long userNo = 1L;

        doThrow(new PostNotFoundException(reply.getPostNo())).when(postReadRepository).findById(reply.getPostNo());

        assertThatThrownBy(() -> replyManageService.createReply(reply.getPostNo(), userNo, request))
                .isInstanceOf(PostNotFoundException.class);

        then(postReadRepository).should(times(1)).findById(reply.getPostNo());
    }


    @Test
    @DisplayName("댓글 수정")
    void modifyReply() {

        ModifyReplyRequestDto request = ReplyDummy.dummyModifyRequest();
        Long userNo = 1L;
        String replaceContent = request.getContent();

        given(replyReadRepository.findById(reply.getReplyNo())).willReturn(Optional.ofNullable(reply));

        ModifyReplyResponseDto actual = replyManageService.modifyReply(reply.getReplyNo(), userNo, request);

        then(replyReadRepository).should(times(1)).findById(reply.getReplyNo());
        assertThat(actual.getContent(), equalTo(replaceContent));
    }

    @Test
    @DisplayName("댓글 삭제 처리")
    void safeDeleteByReplyNo() {

        given(replyReadRepository.findById(reply.getReplyNo())).willReturn(Optional.ofNullable(reply));

        replyManageService.safeDeleteByReplyNo(reply.getReplyNo(), reply.getUserNo());

        then(replyReadRepository).should(times(1)).findById(reply.getReplyNo());
        assertThat(reply.getIsDelete(), is(true));
    }
}