package kr.aling.post.normalpost.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

import kr.aling.post.common.feign.client.UserFeignClient;
import kr.aling.post.normalpost.dto.request.CreateNormalPostRequestDto;
import kr.aling.post.normalpost.dto.request.ModifyNormalPostRequestDto;
import kr.aling.post.normalpost.dto.response.CreateNormalPostResponseDto;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.normalpost.repository.NormalPostManageRepository;
import kr.aling.post.normalpost.service.impl.NormalPostManageServiceImpl;
import kr.aling.post.post.dto.request.CreatePostRequestDto;
import kr.aling.post.post.dto.request.ModifyPostRequestDto;
import kr.aling.post.post.dto.response.CreatePostResponseDto;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.service.PostManageService;
import kr.aling.post.user.dto.response.IsExistsUserResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 일반 게시물 관리 서비스 테스트
 *
 * @author : 이성준
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
class NormalPostManageServiceTest {

    @InjectMocks
    NormalPostManageServiceImpl normalPostManageService;

    @Mock
    PostManageService postManageService;

    @Mock
    UserFeignClient userFeignClient;

    @Mock
    NormalPostManageRepository normalPostManageRepository;

    @Test
    @DisplayName("일반 게시물 생성")
    void createNormalPost() {
        Long userNo = 1L;
        long postNo = 1L;

        Post post = Post.builder().build();
        ReflectionTestUtils.setField(post, "postNo", postNo);

        NormalPost normalPost = NormalPost.builder()
                .post(post)
                .userNo(userNo)
                .build();

        CreateNormalPostRequestDto createNormalPostRequest = new CreateNormalPostRequestDto();

        ReflectionTestUtils.setField(createNormalPostRequest, "content", "테스트용 일반 게시물 내용");
        ReflectionTestUtils.setField(createNormalPostRequest, "isOpen", true);


        IsExistsUserResponseDto responseDto = new IsExistsUserResponseDto();
        ReflectionTestUtils.setField(responseDto, "isExists", true);

        given(postManageService.createPost(any(CreatePostRequestDto.class))).willReturn(
                new CreatePostResponseDto(post));
        given(normalPostManageRepository.save(any(NormalPost.class))).willReturn(normalPost);
        given(userFeignClient.isExistUser(userNo)).willReturn(responseDto);

        CreateNormalPostResponseDto actual = normalPostManageService.createNormalPost(userNo, createNormalPostRequest);

        assertNotNull(actual);
        assertEquals(postNo, actual.getPostNo());

    }

    @Test
    @DisplayName("일반 게시물 수정")
    void modifyNormalPost() {
        Long postNo = 1L;

        ModifyNormalPostRequestDto modifyNormalPostRequest = new ModifyNormalPostRequestDto();

        ReflectionTestUtils.setField(modifyNormalPostRequest, "content", "테스트용 일반 게시물 내용");
        ReflectionTestUtils.setField(modifyNormalPostRequest, "isOpen", true);

        doNothing().when(postManageService).modifyPost(anyLong(), any(ModifyPostRequestDto.class));

        normalPostManageService.modifyNormalPost(postNo, modifyNormalPostRequest);

        then(postManageService).should(times(1)).modifyPost(anyLong(), any(ModifyPostRequestDto.class));
    }

    @Test
    @DisplayName("일반 게시물 삭제")
    void deleteById() {
        Long postNo = 1L;

        normalPostManageService.safeDeleteById(postNo);

        then(postManageService).should(times(1)).safeDeleteById(postNo);
    }
}