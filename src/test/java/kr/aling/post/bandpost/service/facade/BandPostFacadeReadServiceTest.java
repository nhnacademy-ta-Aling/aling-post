package kr.aling.post.bandpost.service.facade;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import kr.aling.post.bandpost.dto.response.BandPostExceptFileQueryDto;
import kr.aling.post.bandpost.dto.response.BandPostQueryDto;
import kr.aling.post.bandpost.dto.response.external.GetFileInfoResponseDto;
import kr.aling.post.bandpost.service.BandPostReadService;
import kr.aling.post.common.feign.client.FileFeignClient;
import kr.aling.post.common.feign.client.UserFeignClient;
import kr.aling.post.postfile.dto.response.PostFileQueryDto;
import kr.aling.post.postfile.service.PostFileReadService;
import kr.aling.post.reply.dto.response.ReadUserInfoResponseDto;
import org.junit.jupiter.api.BeforeEach;
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
class BandPostFacadeReadServiceTest {

    @InjectMocks
    BandPostFacadeReadService bandPostFacadeReadService;

    @Mock
    BandPostReadService bandPostReadService;
    @Mock
    PostFileReadService postFileReadService;
    @Mock
    UserFeignClient userFeignClient;
    @Mock
    FileFeignClient fileFeignClient;

    BandPostQueryDto bandPostQueryDto;
    BandPostExceptFileQueryDto bandPostExceptFileQueryDto;
    GetFileInfoResponseDto getFileInfoResponseDto;
    ReadUserInfoResponseDto getBandPostUserInfoResponseDto;

    @BeforeEach
    void setUp() {
        bandPostQueryDto =
                new BandPostQueryDto(1L, "title", "content", 1L, LocalDateTime.now(), null, false, null, true,
                        List.of(new BandPostQueryDto.AlingFileInfo(1L)));

        bandPostExceptFileQueryDto =
                new BandPostExceptFileQueryDto(1L, "title", "content", 1L, LocalDateTime.now(), null, false, null,
                        true);

        getFileInfoResponseDto = new GetFileInfoResponseDto();
        getBandPostUserInfoResponseDto = new ReadUserInfoResponseDto();
    }

    @Test
    @DisplayName("게시글 단건 조회 파사드 서비스 테스트")
    void getBandPost_facade_service_test() {
        // given

        // when
        when(bandPostReadService.getBandPostInfo(anyLong())).thenReturn(bandPostQueryDto);
        when(fileFeignClient.requestFileInfo(anyList())).thenReturn(List.of(getFileInfoResponseDto));
        when(userFeignClient.requestBandPostUserInfo(anyLong())).thenReturn(getBandPostUserInfoResponseDto);

        // then
        bandPostFacadeReadService.getBandPost(anyLong());

        verify(bandPostReadService, times(1)).getBandPostInfo(anyLong());
        verify(fileFeignClient, times(1)).requestFileInfo(anyList());
        verify(userFeignClient, times(1)).requestBandPostUserInfo(anyLong());
    }

    @Test
    @DisplayName("게시글 페이징 조회 파사드 서비스 테스트")
    void getBandPosts_paging_facade_service_test() {
        // given
        List<BandPostExceptFileQueryDto> list = List.of(bandPostExceptFileQueryDto);
        Page<BandPostExceptFileQueryDto> page = PageableExecutionUtils.getPage(list, PageRequest.of(0, 10), list::size);

        PostFileQueryDto postFileQueryDto = new PostFileQueryDto(1L);

        // when
        when(bandPostReadService.getBandPostsInfoByBand(anyLong(), any())).thenReturn(page);
        when(postFileReadService.getPostFileNoList(anyLong())).thenReturn(List.of(postFileQueryDto));
        when(fileFeignClient.requestFileInfo(anyList())).thenReturn(List.of(getFileInfoResponseDto));
        when(userFeignClient.requestBandPostUserInfo(anyLong())).thenReturn(getBandPostUserInfoResponseDto);

        // then
        bandPostFacadeReadService.getBandPostByBand(1L, PageRequest.of(0, 10));

        verify(bandPostReadService, times(1)).getBandPostsInfoByBand(anyLong(), any());
        verify(postFileReadService, times(1)).getPostFileNoList(anyLong());
        verify(fileFeignClient, times(1)).requestFileInfo(anyList());
        verify(userFeignClient, times(1)).requestBandPostUserInfo(anyLong());
    }

}