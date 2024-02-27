package kr.aling.post.bandposttype.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.aling.post.bandposttype.dto.request.CreateBandPostTypeRequestDto;
import kr.aling.post.bandposttype.entity.BandPostType;
import kr.aling.post.bandposttype.exception.BandPostTypeAlreadyExistsException;
import kr.aling.post.bandposttype.repository.BandPostTypeManageRepository;
import kr.aling.post.bandposttype.repository.BandPostTypeReadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 그룹 게시글 분류 관리 Service 테스트.
 *
 * @author 정유진
 * @since 1.0
 **/
@ExtendWith(SpringExtension.class)
class BandPostTypeManageServiceImplTest {
    @InjectMocks
    BandPostTypeManageServiceImpl bandPostTypeManageService;

    @Mock
    BandPostTypeManageRepository bandPostTypeManageRepository;

    @Mock
    BandPostTypeReadRepository bandPostTypeReadRepository;
    CreateBandPostTypeRequestDto createRequestDto;

    @BeforeEach
    public void setUp() {
        createRequestDto = new CreateBandPostTypeRequestDto();
    }

    @Test
    @DisplayName("게시글 분류 타입 생성 성공")
    void makeBandPostTypeSuccess() {
        // given
        ReflectionTestUtils.setField(createRequestDto, "bandNo", 1L);
        ReflectionTestUtils.setField(createRequestDto, "bandPostTypeName", "testTypeName");

        // when
        when(bandPostTypeReadRepository.existsByNameAndBandNo(anyString(), anyLong()))
                .thenReturn(false);
        when(bandPostTypeManageRepository.save(any(BandPostType.class))).thenReturn(mock(BandPostType.class));

        // then
        assertDoesNotThrow(() -> bandPostTypeManageService.makeBandPostType(createRequestDto));

        verify(bandPostTypeReadRepository, times(1)).existsByNameAndBandNo(anyString(), anyLong());
        verify(bandPostTypeManageRepository, times(1)).save(any(BandPostType.class));
    }

    @Test
    @DisplayName("게시글 분류 타입 생성 실패")
    void makeBandPostTypeFail() {
        // given
        ReflectionTestUtils.setField(createRequestDto, "bandNo", 1L);
        ReflectionTestUtils.setField(createRequestDto, "bandPostTypeName", "testTypeName");

        // when
        when(bandPostTypeReadRepository.existsByNameAndBandNo(anyString(), anyLong()))
                .thenReturn(true);
        when(bandPostTypeManageRepository.save(any(BandPostType.class))).thenReturn(mock(BandPostType.class));

        // then
        assertThatThrownBy(() -> bandPostTypeManageService.makeBandPostType(createRequestDto))
                .isInstanceOf(BandPostTypeAlreadyExistsException.class)
                .hasMessageContaining(BandPostTypeAlreadyExistsException.MESSAGE);

        verify(bandPostTypeReadRepository, times(1)).existsByNameAndBandNo(anyString(), anyLong());
        verify(bandPostTypeManageRepository, times(0)).save(any(BandPostType.class));
    }
}