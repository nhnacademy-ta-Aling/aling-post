package kr.aling.post.post.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Optional;
import kr.aling.post.post.dto.response.ReadPostResponse;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.exception.PostNotFoundException;
import kr.aling.post.post.repository.PostReadRepository;
import kr.aling.post.post.service.impl.PostReadServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author : 이성준
 * @since : 1.0
 */


@ExtendWith(SpringExtension.class)
class PostReadServiceTest {

    @Mock
    PostReadRepository postReadRepository;

    @InjectMocks
    PostReadServiceImpl postReadService;

    @Test
    @DisplayName("게시물 조회시 올바른 응답 객체 반환")
    void readPostByPostNo() {
        ReadPostResponse postResponse = new ReadPostResponse();
        Post post = Post.builder().build();

        LocalDateTime createAt = LocalDateTime.now();

        ReflectionTestUtils.setField(post, "postNo", 1L);
        ReflectionTestUtils.setField(post, "content", "게시물 내용");
        ReflectionTestUtils.setField(post, "createAt", createAt);
        ReflectionTestUtils.setField(post, "modifyAt", null);

        ReflectionTestUtils.setField(postResponse, "postNo", 1L);
        ReflectionTestUtils.setField(postResponse, "content", "게시물 내용");
        ReflectionTestUtils.setField(postResponse, "createAt", createAt);
        ReflectionTestUtils.setField(postResponse, "modifyAt", null);

        given(postReadRepository.findById(postResponse.getPostNo())).willReturn(Optional.of(post));

        ReadPostResponse actual = postReadService.readPostByPostNo(postResponse.getPostNo());

        assertAll("게시물 내용과 응답 DTO 가 동일한지 확인",
                ()-> assertThat(postResponse.getPostNo(), equalTo(actual.getPostNo())),
                ()-> assertThat(postResponse.getContent(), equalTo(actual.getContent())),
                ()-> assertThat(postResponse.getCreateAt(), equalTo(actual.getCreateAt())),
                ()-> assertThat(postResponse.getModifyAt(), equalTo(actual.getModifyAt()))
        );
    }

    @Test
    @DisplayName("게시물 번호에 따른 게시물 엔티티 반환")
    void findById() {
        Post post = Post.builder().build();

        LocalDateTime createAt = LocalDateTime.now();

        ReflectionTestUtils.setField(post, "postNo", 1L);
        ReflectionTestUtils.setField(post, "content", "게시물 내용");
        ReflectionTestUtils.setField(post, "createAt", createAt);
        ReflectionTestUtils.setField(post, "modifyAt", null);

        given(postReadRepository.findById(post.getPostNo())).willReturn(Optional.of(post));

        Post actual = postReadService.findById(post.getPostNo());

        assertEquals(post, actual);
    }

    @Test
    @DisplayName("존재하지 않는 게시물 번호에 대한 조회시도")
    void readPostThenThrow() {
        Long postNo = 2L;

        given(postReadRepository.findById(postNo)).willThrow(new PostNotFoundException(postNo));

        assertThrows(PostNotFoundException.class,()-> postReadService.readPostByPostNo(postNo),"Post Not Found : " + postNo);

    }
}