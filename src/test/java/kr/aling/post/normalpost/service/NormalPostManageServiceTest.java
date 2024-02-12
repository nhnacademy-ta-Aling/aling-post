package kr.aling.post.normalpost.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

import kr.aling.post.normalpost.dto.request.CreateNormalPostRequest;
import kr.aling.post.normalpost.dto.request.ModifyNormalPostRequest;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.normalpost.repository.NormalPostManageRepository;
import kr.aling.post.normalpost.service.impl.NormalPostManageServiceImpl;
import kr.aling.post.post.dto.request.CreatePostRequest;
import kr.aling.post.post.dto.request.ModifyPostRequest;
import kr.aling.post.post.service.PostManageService;
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
class NormalPostManageServiceTest {

    @InjectMocks
    NormalPostManageServiceImpl normalPostManageService;

    @Mock
    PostManageService postManageService;

    @Mock
    NormalPostManageRepository normalPostManageRepository;

    @Test
    @DisplayName("일반 게시물 생성")
    void createNormalPost() {
        Long userNo = 1L;
        Long postNo = 1L;

        NormalPost normalPost = NormalPost.builder()
                .userNo(userNo)
                .postNo(postNo)
                .build();

        CreateNormalPostRequest createNormalPostRequest = new CreateNormalPostRequest();

        ReflectionTestUtils.setField(createNormalPostRequest, "content", "테스트용 일반 게시물 내용");
        ReflectionTestUtils.setField(createNormalPostRequest, "isOpen", true);

        given(postManageService.createPost(any(CreatePostRequest.class))).willReturn(postNo);
        given(normalPostManageRepository.save(any(NormalPost.class))).willReturn(normalPost);

        Long actual = normalPostManageService.createNormalPost(userNo, createNormalPostRequest);

        assertNotNull(actual);
        assertEquals(postNo, actual);

    }

    @Test
    @DisplayName("일반 게시물 수정")
    void modifyNormalPost() {
        Long postNo = 1L;

        ModifyNormalPostRequest modifyNormalPostRequest = new ModifyNormalPostRequest();

        ReflectionTestUtils.setField(modifyNormalPostRequest, "content", "테스트용 일반 게시물 내용");
        ReflectionTestUtils.setField(modifyNormalPostRequest, "isOpen", true);

        doNothing().when(postManageService).modifyPost(anyLong(), any(ModifyPostRequest.class));

        normalPostManageService.modifyNormalPost(postNo, modifyNormalPostRequest);

        then(postManageService).should(times(1)).modifyPost(anyLong(), any(ModifyPostRequest.class));
    }

    @Test
    @DisplayName("일반 게시물 삭제")
    void deleteById() {
        Long postNo = 1L;

        normalPostManageService.deleteById(postNo);

        then(postManageService).should(times(1)).deleteById(postNo);
        then(normalPostManageRepository).should(times(1)).deleteById(postNo);
    }
}