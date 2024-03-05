package kr.aling.post.normalpost.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.common.utils.PostUtils;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.normalpost.repository.NormalPostReadRepository;
import kr.aling.post.normalpost.service.impl.NormalPostReadServiceImpl;
import kr.aling.post.post.dto.response.ReadPostResponseDto;
import kr.aling.post.post.entity.Post;
import kr.aling.post.reply.dto.response.ReadWriterResponseDto;
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
 * 일반 게시물 조회 서비스 테스트
 *
 * @author : 이성준
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
class NormalPostReadServiceTest {

    @Mock
    NormalPostReadRepository normalPostReadRepository;

    @InjectMocks
    NormalPostReadServiceImpl normalPostReadService;

    @Test
    @DisplayName("게시물 번호로 일반 게시물의 응답 객체 반환")
    void readNormalPostByPostNo() {
        long userNo = 1L;
        long postNo = 1L;

        Post post = Post.builder().build();
        ReflectionTestUtils.setField(post, "postNo", postNo);

        NormalPost normalPost = NormalPost.builder()
                .post(post)
                .userNo(userNo)
                .build();

        ReadWriterResponseDto writerResponse = new ReadWriterResponseDto(1L, "테스트 작성자");

        ReadPostResponseDto response = PostUtils.convert(post);

        given(normalPostReadRepository.findById(postNo)).willReturn(Optional.of(normalPost));

        ReadPostResponseDto actual = normalPostReadService.readNormalPostByPostNo(postNo);

        assertAll("게시물 내용과 응답 DTO 가 동일한지 확인",
                () -> assertThat(response.getPostNo(), equalTo(actual.getPostNo())),
                () -> assertThat(response.getContent(), equalTo(actual.getContent()))
        );
    }

    @Test
    @DisplayName("유저 번호에 대한 게시물 리스트 조회")
    void readNormalPostsByUserNo() {
        long userNo = 1L;
        long postNo = 1L;

        Post post = Post.builder().build();
        ReflectionTestUtils.setField(post, "postNo", postNo);

        ReadWriterResponseDto writerResponseDto = new ReadWriterResponseDto(1L, "테스트 작성자");

        Page<ReadPostResponseDto> responses = new PageImpl<>(List.of(PostUtils.convert(post
        )));

        given(normalPostReadRepository.findAllByUserNo(userNo, Pageable.unpaged())).willReturn(responses);

        PageResponseDto<ReadPostResponseDto> actual =
                normalPostReadService.readNormalPostsByUserNo(userNo, Pageable.unpaged());

        assertAll(
                "",
                () -> {
                    assertThat(actual, is(not(nullValue())));
                    assertThat(actual.getContent(), equalTo(responses.getContent()));
                    assertThat(actual.getTotalPages(), equalTo(responses.getTotalPages()));
                }
        );
    }
}