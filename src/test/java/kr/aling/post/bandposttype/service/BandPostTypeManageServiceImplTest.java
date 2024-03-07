package kr.aling.post.bandposttype.service;

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

import java.util.Optional;
import kr.aling.post.bandpost.repository.BandPostReadRepository;
import kr.aling.post.bandposttype.dto.request.CreateBandPostTypeRequestDto;
import kr.aling.post.bandposttype.dto.request.ModifyBandPostTypeRequestDto;
import kr.aling.post.bandposttype.entity.BandPostType;
import kr.aling.post.bandposttype.exception.BandPostTypeAlreadyExistsException;
import kr.aling.post.bandposttype.exception.BandPostTypeDeniedException;
import kr.aling.post.bandposttype.exception.BandPostTypeNotFoundException;
import kr.aling.post.bandposttype.repository.BandPostTypeManageRepository;
import kr.aling.post.bandposttype.repository.BandPostTypeReadRepository;
import kr.aling.post.bandposttype.service.impl.BandPostTypeManageServiceImpl;
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
    @Mock
    BandPostReadRepository bandPostReadRepository;

    CreateBandPostTypeRequestDto createRequestDto;

    @BeforeEach
    public void setUp() {
        createRequestDto = new CreateBandPostTypeRequestDto();
    }

    @Test
    @DisplayName("그룹 게시글 분류 생성 성공")
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
    @DisplayName("그룹 게시글 분류 생성 실패")
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

    @Test
    @DisplayName("그룹 게시글 분류 수정 성공")
    void updateBandPostType_successTest() {
        // given
        Long postTypeNo = 1L;
        ModifyBandPostTypeRequestDto modifyRequestDto = new ModifyBandPostTypeRequestDto();

        ReflectionTestUtils.setField(modifyRequestDto, "bandNo", 1L);
        ReflectionTestUtils.setField(modifyRequestDto, "bandPostTypeName", "typeName");

        // when
        when(bandPostTypeReadRepository.existsByNameAndBandNo(anyString(), anyLong()))
                .thenReturn(false);
        when(bandPostTypeReadRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(mock(BandPostType.class)));

        // then
        assertDoesNotThrow(() -> bandPostTypeManageService.updateBandPostType(postTypeNo, modifyRequestDto));

        verify(bandPostTypeReadRepository, times(1)).existsByNameAndBandNo(anyString(), anyLong());
        verify(bandPostTypeReadRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("그룹 게시글 분류 수정 실패_같은 그룹 내 이미 같은 그룹 게시글 분류 이름이 있는 경우")
    void updateBandPostType_failTest_bandPostTypeAlreadyExistsException() {
        // given
        Long postTypeNo = 1L;
        ModifyBandPostTypeRequestDto modifyRequestDto = new ModifyBandPostTypeRequestDto();

        ReflectionTestUtils.setField(modifyRequestDto, "bandNo", 1L);
        ReflectionTestUtils.setField(modifyRequestDto, "bandPostTypeName", "typeName");

        // when
        when(bandPostTypeReadRepository.existsByNameAndBandNo(anyString(), anyLong()))
                .thenReturn(true);
        when(bandPostTypeReadRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(mock(BandPostType.class)));

        // then
        assertThatThrownBy(() -> bandPostTypeManageService.updateBandPostType(postTypeNo, modifyRequestDto))
                .isInstanceOf(BandPostTypeAlreadyExistsException.class)
                .hasMessageContaining(BandPostTypeAlreadyExistsException.MESSAGE);

        verify(bandPostTypeReadRepository, times(1)).existsByNameAndBandNo(anyString(), anyLong());
        verify(bandPostTypeReadRepository, times(0)).findById(anyLong());
    }

    @Test
    @DisplayName("그룹 게시글 분류 수정 실패_그룹 게시글 분류를 찾을 수 없는 경우")
    void updateBandPostType_failTest_bandPostTypeNotFoundException() {
        // given
        Long postTypeNo = 1L;
        ModifyBandPostTypeRequestDto modifyRequestDto = new ModifyBandPostTypeRequestDto();

        ReflectionTestUtils.setField(modifyRequestDto, "bandNo", 1L);
        ReflectionTestUtils.setField(modifyRequestDto, "bandPostTypeName", "typeName");

        // when
        when(bandPostTypeReadRepository.existsByNameAndBandNo(anyString(), anyLong()))
                .thenReturn(false);
        when(bandPostTypeReadRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> bandPostTypeManageService.updateBandPostType(postTypeNo, modifyRequestDto))
                .isInstanceOf(BandPostTypeNotFoundException.class)
                .hasMessageContaining(BandPostTypeNotFoundException.MESSAGE);

        verify(bandPostTypeReadRepository, times(1)).existsByNameAndBandNo(anyString(), anyLong());
        verify(bandPostTypeReadRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("그룹 게시글 분류 삭제 성공")
    void deleteBandPostType_successTest() {
        // given
        Long postTypeNo = 1L;

        // when
        when(bandPostTypeReadRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mock(BandPostType.class)));
        when(bandPostReadRepository.getCountBandPostByBandPostTypeNo(anyLong())).thenReturn(0L);

        // then
        assertDoesNotThrow(() -> bandPostTypeManageService.deleteBandPostType(postTypeNo));

        verify(bandPostTypeReadRepository, times(1)).findById(anyLong());
        verify(bandPostReadRepository, times(1)).getCountBandPostByBandPostTypeNo(anyLong());
    }

    @Test
    @DisplayName("그룹 게시글 분류 삭제 실패_그룹 게시글 분류가 존재 하지 않는 경우")
    void deleteBandPostType_failTest_bandPostTypeNotFoundException() {
        // given
        Long postTypeNo = 1L;

        // when
        when(bandPostTypeReadRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(bandPostReadRepository.getCountBandPostByBandPostTypeNo(anyLong())).thenReturn(0L);

        // then
        assertThatThrownBy(() -> bandPostTypeManageService.deleteBandPostType(postTypeNo))
                .isInstanceOf(BandPostTypeNotFoundException.class)
                .hasMessageContaining(BandPostTypeNotFoundException.MESSAGE);

        verify(bandPostTypeReadRepository, times(1)).findById(anyLong());
        verify(bandPostReadRepository, times(0)).getCountBandPostByBandPostTypeNo(anyLong());
    }

    @Test
    @DisplayName("그룹 게시글 분류 삭제 실패_그룹 게시글 분류에 삭제되지 않은 게시글이 존재하는 경우")
    void deleteBandPostType_failTest_bandPostTypeDeniedException() {
        // given
        Long postTypeNo = 1L;

        // when
        when(bandPostTypeReadRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mock(BandPostType.class)));
        when(bandPostReadRepository.getCountBandPostByBandPostTypeNo(anyLong())).thenReturn(1L);

        // then
        assertThatThrownBy(() -> bandPostTypeManageService.deleteBandPostType(postTypeNo))
                .isInstanceOf(BandPostTypeDeniedException.class)
                .hasMessageContaining(BandPostTypeDeniedException.MESSAGE);

        verify(bandPostTypeReadRepository, times(1)).findById(anyLong());
        verify(bandPostReadRepository, times(1)).getCountBandPostByBandPostTypeNo(anyLong());
    }
}