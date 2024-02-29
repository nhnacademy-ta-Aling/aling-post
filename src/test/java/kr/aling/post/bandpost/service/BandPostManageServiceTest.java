package kr.aling.post.bandpost.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import kr.aling.post.bandpost.dto.request.CreateBandPostRequestDto;
import kr.aling.post.bandpost.entity.BandPost;
import kr.aling.post.bandpost.repository.BandPostManageRepository;
import kr.aling.post.bandpost.service.impl.BandPostManageServiceImpl;
import kr.aling.post.bandposttype.dummy.BandPostTypeDummy;
import kr.aling.post.bandposttype.entity.BandPostType;
import kr.aling.post.bandposttype.exception.BandPostTypeNotFoundException;
import kr.aling.post.bandposttype.repository.BandPostTypeReadRepository;
import kr.aling.post.post.dto.response.CreatePostResponseDto;
import kr.aling.post.post.dummy.PostDummy;
import kr.aling.post.post.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
class BandPostManageServiceTest {

    @InjectMocks
    BandPostManageServiceImpl bandPostManageService;

    @Mock
    BandPostManageRepository bandPostManageRepository;

    @Mock
    BandPostTypeReadRepository bandPostTypeReadRepository;

    Post post;
    CreatePostResponseDto createPostResponseDto;
    CreateBandPostRequestDto createBandPostRequestDto;

    @BeforeEach
    void setUp() {
        post = PostDummy.postDummy();
        createPostResponseDto = new CreatePostResponseDto(post);

        createBandPostRequestDto = new CreateBandPostRequestDto();
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTitle", "title");
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostContent", "content");
        ReflectionTestUtils.setField(createBandPostRequestDto, "isOpen", false);
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTypeNo", 1L);
        ReflectionTestUtils.setField(createBandPostRequestDto, "fileNoList", List.of(1L));
    }

    @Test
    @DisplayName("bandPost 생성 테스트 (bandPostType 있는 상황)")
    void create_bandPost_test_with_bandPostType() {
        // given
        BandPostType bandPostType = BandPostTypeDummy.bandPostTypeDummy();

        // when
        when(bandPostTypeReadRepository.findById(anyLong())).thenReturn(Optional.of(bandPostType));

        // then
        bandPostManageService.createBandPost(createPostResponseDto, createBandPostRequestDto, 1L);

        verify(bandPostManageRepository, times(1)).save(any(BandPost.class));
        verify(bandPostTypeReadRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("bandPost 생성 실패 테스트 (bandPostType not found)")
    void create_bandPost_fail_test_notFound_bandPostType() {
        // given

        // when
        when(bandPostTypeReadRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> bandPostManageService.createBandPost(createPostResponseDto,
                createBandPostRequestDto, 1L))
                .isInstanceOf(BandPostTypeNotFoundException.class)
                .hasMessageContaining(BandPostTypeNotFoundException.MESSAGE);
    }

}
