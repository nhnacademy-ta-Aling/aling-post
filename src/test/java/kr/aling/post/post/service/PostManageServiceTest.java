package kr.aling.post.post.service;

import static org.assertj.core.api.Assertions.assertThat;
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
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import kr.aling.post.bandpost.dto.request.CreateBandPostRequestDto;
import kr.aling.post.post.dto.request.CreatePostRequestDto;
import kr.aling.post.post.dto.request.ModifyPostRequestDto;
import kr.aling.post.post.dto.response.CreatePostResponseDtoTmp;
import kr.aling.post.post.dummy.PostDummy;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.exception.PostNotFoundException;
import kr.aling.post.post.repository.PostManageRepository;
import kr.aling.post.post.repository.PostReadRepository;
import kr.aling.post.post.service.impl.PostManageServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 게시물 관리 서비스 테스트
 *
 * @author : 이성준
 * @since : 1.0
 */
@ExtendWith(SpringExtension.class)
class PostManageServiceTest {

    @Mock
    PostReadRepository postReadRepository;

    @Mock
    PostManageRepository postManageRepository;

    @InjectMocks
    PostManageServiceImpl postManageService;

    @Test
    @DisplayName("게시물 작성 요청에 따른 게시물 생성")
    void createPost() {
        Long postNo = 1L;
        CreatePostRequestDto createPostRequest = new CreatePostRequestDto("게시물 작성 내용", true);

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
        ModifyPostRequestDto modifyPostRequest = new ModifyPostRequestDto(
                "수정 후 게시물 내용",
                false
        );

        String contentBeforeModify = "수정 전 게시물 내용";
        boolean isOpenBeforeModify = true;

        Post post = Post.builder()
                .content(contentBeforeModify)
                .isOpen(isOpenBeforeModify)
                .build();

        given(postReadRepository.findById(postNo)).willReturn(Optional.of(post));

        postManageService.modifyPost(postNo, modifyPostRequest);

        assertThat(isOpenBeforeModify, not(equalTo(modifyPostRequest.getIsOpen())));
        assertThat(post.getIsOpen(), equalTo(modifyPostRequest.getIsOpen()));
    }

    @Test
    @DisplayName("게시물 비공개 처리")
    void privatePost() {
        Long postNo = 1L;
        Post post = Post.builder()
                .content("비공개 처리 테스트 게시물 내용")
                .isOpen(true)
                .build();

        given(postReadRepository.findById(postNo)).willReturn(Optional.of(post));

        postManageService.privatePost(postNo);

        assertFalse(post.getIsOpen());
    }

    @Test
    @DisplayName("존재하지 않는 게시물 비공개 처리시 예외 발생")
    void privatePostButNotExisted() {
        Long postNo = 2L;

        given(postReadRepository.findById(postNo)).willThrow(new PostNotFoundException(postNo));

        assertThrows(PostNotFoundException.class, () -> postManageService.privatePost(postNo));
    }

    @Test
    @DisplayName("게시물 삭제")
    void deleteById() {
        Long postNo = 1L;
        Post post = Post.builder()
                .content("삭제 처리 테스트 게시물 내용")
                .isOpen(true)
                .build();

        given(postReadRepository.findById(postNo)).willReturn(Optional.of(post));

        postManageService.safeDeleteById(postNo);

        assertTrue(post.getIsDelete());
    }

    @Test
    @DisplayName("그룹 게시물 전용 저장 테스트")
    void bandPost_create_test() {
        // given
        CreateBandPostRequestDto createBandPostRequestDto = new CreateBandPostRequestDto();
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTitle", "title");
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostContent", "content");
        ReflectionTestUtils.setField(createBandPostRequestDto, "isOpen", false);
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTypeNo", 1L);
        ReflectionTestUtils.setField(createBandPostRequestDto, "fileNoList", List.of(1L));

        Post post = PostDummy.postDummy();

        // when
        when(postManageRepository.save(any(Post.class))).thenReturn(post);

        // then
        CreatePostResponseDtoTmp result = postManageService.createBandPost(createBandPostRequestDto);

        assertThat(result).isNotNull();
        assertThat(result.getPost().getPostNo()).isEqualTo(post.getPostNo());
    }
}