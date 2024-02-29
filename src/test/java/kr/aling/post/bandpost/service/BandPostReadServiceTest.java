package kr.aling.post.bandpost.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import kr.aling.post.bandpost.dto.response.BandPostExceptFileQueryDto;
import kr.aling.post.bandpost.exception.BandPostNotFoundException;
import kr.aling.post.bandpost.repository.BandPostReadRepository;
import kr.aling.post.bandpost.service.impl.BandPostReadServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class BandPostReadServiceTest {

    @InjectMocks
    BandPostReadServiceImpl bandPostReadService;

    @Mock
    BandPostReadRepository bandPostReadRepository;

    @Test
    @DisplayName("그룹 게시글 정보 조회 성공 서비스 테스트")
    void getBandPostInfo_service_success_test() {
        // given

        // when
        when(bandPostReadRepository.existsById(anyLong())).thenReturn(true);

        // then
        bandPostReadService.getBandPostInfo(anyLong());

        verify(bandPostReadRepository, times(1)).getBandPostByPostNo(anyLong());
    }

    @Test
    @DisplayName("그룹 게시물 정보 조회 실패 테스트 - 없는 postNo")
    void getBandPostInfo_service_fail_test_no_postNo() {
        // given

        // when
        when(bandPostReadRepository.existsById(anyLong())).thenReturn(false);

        // then
        assertThatThrownBy(() -> bandPostReadService.getBandPostInfo(1L))
                .isInstanceOf(BandPostNotFoundException.class)
                .hasMessage(BandPostNotFoundException.MESSAGE);
    }

    @Test
    @DisplayName("그룹의 게시글 페이징 조회 성공 테스트")
    void getBandPost_paging_service_success_test() {
        // given
        BandPostExceptFileQueryDto bandPostExceptFileQueryDto =
                new BandPostExceptFileQueryDto(1L, "title", "content", 1L, LocalDateTime.now(), null, false, null,
                        true);
        List<BandPostExceptFileQueryDto> list = List.of(bandPostExceptFileQueryDto);
        Page<BandPostExceptFileQueryDto> page = PageableExecutionUtils.getPage(list, PageRequest.of(0, 10), list::size);

        // when
        when(bandPostReadRepository.getBandPostByBand(anyLong(), any())).thenReturn(page);

        // then
        bandPostReadService.getBandPostsInfoByBand(anyLong(), any());

        verify(bandPostReadRepository, times(1)).getBandPostByBand(anyLong(), any());
    }

}