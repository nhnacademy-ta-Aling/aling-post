package kr.aling.post.post.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import feign.FeignException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.aling.post.common.utils.PostUtils;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.post.dto.response.IsExistsPostResponseDto;
import kr.aling.post.post.dto.response.ReadPostResponseIntegrationDto;
import kr.aling.post.post.dto.response.ReadPostsForScrapResponseDto;
import kr.aling.post.post.dummy.PostDummy;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.exception.PostNotFoundException;
import kr.aling.post.post.repository.PostReadRepository;
import kr.aling.post.post.service.impl.PostReadServiceImpl;
import kr.aling.post.postfile.adaptor.PostFileAdaptor;
import kr.aling.post.postscrap.dto.response.ReadPostScrapsPostResponseDto;
import kr.aling.post.reply.dto.response.ReadUserInfoResponseDto;
import kr.aling.post.user.adaptor.AuthorInformationAdaptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 게시물 조회 서비스 테스트
 *
 * @author : 이성준
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
class PostReadServiceTest {

    @Mock
    PostReadRepository postReadRepository;

    @Mock
    AuthorInformationAdaptor authorInformationAdaptor;

    @Mock
    PostFileAdaptor postFileAdaptor;

    @InjectMocks
    PostReadServiceImpl postReadService;

    @Test
    @DisplayName("게시물 조회시 올바른 응답 객체 반환")
    void readPostByPostNo() {

        Post post = PostDummy.postDummy();
        LocalDateTime createAt = LocalDateTime.now();

        ReflectionTestUtils.setField(post, "postNo", 1L);
        ReflectionTestUtils.setField(post, "createAt", createAt);
        ReflectionTestUtils.setField(post, "modifyAt", null);

        NormalPost normalPost = NormalPost.builder()
                .post(post)
                .build();

        ReflectionTestUtils.setField(post, "normalPost", normalPost);

        ReadUserInfoResponseDto writerResponse = new ReadUserInfoResponseDto(1L, "테스트 작성자", null);

        ReadPostResponseIntegrationDto integrationDto = ReadPostResponseIntegrationDto.builder()
                .post(PostUtils.convert(post))
                .writer(writerResponse)
                .additional(null)
                .build();

        given(postReadRepository.findByPostNoAndIsDeleteFalseAndIsOpenTrue(post.getPostNo())).willReturn(
                Optional.of(post));
        doThrow(FeignException.class).when(authorInformationAdaptor).readPostAuthorInfo(any());

        ReadPostResponseIntegrationDto actual = postReadService.readPostByPostNo(post.getPostNo());

        assertAll("게시물 내용과 응답 DTO 가 동일한지 확인",
                () -> assertThat(integrationDto.getPost().getPostNo(), equalTo(actual.getPost().getPostNo())),
                () -> assertThat(integrationDto.getPost().getContent(), equalTo(actual.getPost().getContent())),
                () -> assertThat(integrationDto.getPost().getCreateAt(), equalTo(actual.getPost().getCreateAt())),
                () -> assertThat(integrationDto.getPost().getModifyAt(), equalTo(actual.getPost().getModifyAt()))
        );
    }

    @Test
    @DisplayName("존재하지 않는 게시물 번호에 대한 조회시도")
    void readPostThenThrow() {
        Long postNo = 2L;

        given(postReadRepository.findById(postNo)).willThrow(new PostNotFoundException(postNo));

        assertThrows(PostNotFoundException.class, () -> postReadService.readPostByPostNo(postNo),
                "Post Not Found : " + postNo);
    }

    @Test
    @DisplayName("게시물 존재 확인 성공")
    void isExistsPost() {
        // given
        Long postNo = 1L;

        when(postReadRepository.existsById(anyLong())).thenReturn(Boolean.TRUE);

        // when
        IsExistsPostResponseDto result = postReadService.isExistsPost(postNo);

        // then
        assertEquals(Boolean.TRUE, result.getIsExists());
    }

    @Test
    @DisplayName("게시물 번호로 게시물 내용 조회 성공")
    void getPostsForScrap() {
        // given
        List<Long> postNos = List.of(1L, 2L, 3L);

        List<ReadPostScrapsPostResponseDto> list = List.of(
                new ReadPostScrapsPostResponseDto(1L, "1", false, true),
                new ReadPostScrapsPostResponseDto(2L, "2", true, true),
                new ReadPostScrapsPostResponseDto(3L, "3", true, false)
        );
        when(postReadRepository.getPostInfoForScrap(anyList())).thenReturn(list);

        // when
        ReadPostsForScrapResponseDto result = postReadService.getPostsForScrap(postNos);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getInfos()).isEqualTo(list);
    }
}