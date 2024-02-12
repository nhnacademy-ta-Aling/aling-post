package kr.aling.post.post.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import kr.aling.post.post.dto.request.CreatePostRequest;
import kr.aling.post.post.dto.request.ModifyPostRequest;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.exception.PostNotFoundException;
import kr.aling.post.post.repository.PostManageRepository;
import kr.aling.post.post.service.impl.PostManageServiceImpl;
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
class PostManageServiceTest {

    @Mock
    PostReadService postReadService;

    @Mock
    PostManageRepository postManageRepository;

    @InjectMocks
    PostManageServiceImpl postManageService;

    @Test
    @DisplayName("게시물 작성 요청에 따른 게시물 생성")
    void createPost() {
        Long postNo = 1L;
        CreatePostRequest createPostRequest = new CreatePostRequest();

        ReflectionTestUtils.setField(createPostRequest,"isOpen",true);
        ReflectionTestUtils.setField(createPostRequest,"content", "게시물 작성 내용");

        Post post = Post.builder()
                .content(createPostRequest.getContent())
                .isOpen(createPostRequest.getIsOpen())
                .build();

        ReflectionTestUtils.setField(post, "postNo", postNo);

        given(postManageRepository.save(any(Post.class))).willReturn(post);

        postManageService.createPost(createPostRequest);

        then(postManageRepository).should(times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("게시물 수정 요청에 따른 게시물 수정")
    void modifyPost() {
        Long postNo = 1L;
        ModifyPostRequest modifyPostRequest = new ModifyPostRequest();

        ReflectionTestUtils.setField(modifyPostRequest,"isOpen",false);
        ReflectionTestUtils.setField(modifyPostRequest,"content", "게시물 작성 내용");

        String contentBeforeModify = "수정 전 게시물 내용";
        boolean isOpenBeforeModify = true;


        Post post = Post.builder()
                .content(contentBeforeModify)
                .isOpen(isOpenBeforeModify)
                .build();

        given(postReadService.findById(postNo)).willReturn(post);

        postManageService.modifyPost(postNo, modifyPostRequest);

        assertThat(isOpenBeforeModify, not(equalTo(modifyPostRequest.getIsOpen())));
        assertThat(post.isOpen(), equalTo(modifyPostRequest.getIsOpen()));
    }

    @Test
    @DisplayName("게시물 비공개 처리")
    void privatePost() {
        Long postNo = 1L;
        Post post = Post.builder()
                .content("")
                .isOpen(true)
                .build();

        given(postReadService.findById(postNo)).willReturn(post);

        postManageService.privatePost(postNo);

        assertFalse(post.isOpen());
    }

    @Test
    @DisplayName("존재하지 않는 게시물 비공개 처리시 예외 발생")
    void privatePostButNotExisted() {
        Long postNo = 2L;

        given(postReadService.findById(postNo)).willThrow(new PostNotFoundException(postNo));

        assertThrows(PostNotFoundException.class, ()-> postManageService.privatePost(postNo));
    }

    @Test
    @DisplayName("게시물 삭제")
    void deleteById() {
        Long postNo = 1L;
        Post post = Post.builder()
                .content("")
                .isOpen(true)
                .build();

        given(postReadService.findById(postNo)).willReturn(post);

        postManageService.deleteById(postNo);

        assertTrue(post.getIsDelete());
    }
}