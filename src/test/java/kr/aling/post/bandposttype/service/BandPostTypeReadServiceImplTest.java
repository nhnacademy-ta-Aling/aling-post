package kr.aling.post.bandposttype.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.aling.post.bandposttype.dto.response.GetBandPostTypeResponseDto;
import kr.aling.post.bandposttype.repository.BandPostTypeReadRepository;
import kr.aling.post.bandposttype.service.impl.BandPostTypeReadServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 그룹 게시글 분류 조회 Service 테스트.
 *
 * @author 정유진
 * @since 1.0
 **/
@ExtendWith(SpringExtension.class)
class BandPostTypeReadServiceImplTest {
    @InjectMocks
    BandPostTypeReadServiceImpl bandPostTypeReadService;

    @Mock
    BandPostTypeReadRepository bandPostTypeReadRepository;

    GetBandPostTypeResponseDto getResponseDto;

    @BeforeEach
    void setUp() {
        getResponseDto = new GetBandPostTypeResponseDto(1L, "testTypeName");
    }

    @Test
    @DisplayName("특정 그룹의 게시글 분류 타입 리스트 조회")
    void getBandPostTypeListSuccess() {
        // given

        // when
        when(bandPostTypeReadRepository.getBandPostTypeListByBandNo(anyLong())).thenReturn(List.of(getResponseDto));

        // then
        assertDoesNotThrow(() -> bandPostTypeReadService.getBandPostTypeList(1L));

        verify(bandPostTypeReadRepository, times(1)).getBandPostTypeListByBandNo(anyLong());
    }
}