package kr.aling.post.bandpost.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.aling.post.bandpost.dto.request.CreateBandPostRequestDto;
import kr.aling.post.bandpost.service.facade.BandPostFacadeService;
import kr.aling.post.post.dto.response.CreatePostResponseDto;
import kr.aling.post.post.dummy.PostDummy;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.service.PostManageService;
import kr.aling.post.postfile.service.PostFileManageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
class BandPostFacadeServiceTest {

    @InjectMocks
    BandPostFacadeService bandPostFacadeService;

    @Mock
    PostManageService postManageService;

    @Mock
    PostFileManageService postFileManageService;

    @Mock
    BandPostManageService bandPostManageService;

    CreatePostResponseDto createPostResponseDto;
    CreateBandPostRequestDto createBandPostRequestDto;

    @BeforeEach
    void setUp() {
        Post post = PostDummy.postDummy();
        createPostResponseDto = new CreatePostResponseDto(post);

        createBandPostRequestDto = new CreateBandPostRequestDto();

        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTitle", "title");
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostContent", "content");
        ReflectionTestUtils.setField(createBandPostRequestDto, "isOpen", false);
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTypeNo", 1L);
        ReflectionTestUtils.setField(createBandPostRequestDto, "fileNoList", List.of(1L));
    }

    @Test
    @DisplayName("BansPost Facade 서비스 test")
    void bandPost_facade_service_test() {
        // given

        // when
        when(postManageService.createPost(any(CreateBandPostRequestDto.class))).thenReturn(createPostResponseDto);
        doNothing().when(postFileManageService).savePostFiles(any(), anyList());
        doNothing().when(bandPostManageService).createBandPost(any(), any(), anyLong());

        // then
        bandPostFacadeService.createBandPostFacade(createBandPostRequestDto, 1L);

        verify(postManageService, times(1)).createPost(any(CreateBandPostRequestDto.class));
        verify(postFileManageService, times(1)).savePostFiles(any(Post.class), anyList());
        verify(bandPostManageService, times(1)).createBandPost(any(CreatePostResponseDto.class),
                any(CreateBandPostRequestDto.class), anyLong());
    }

}