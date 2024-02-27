package kr.aling.post.bandpost.service.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import kr.aling.post.bandpost.dto.response.BandPostDto;
import kr.aling.post.bandpost.dto.response.BandPostExceptFileQueryDto;
import kr.aling.post.bandpost.dto.response.BandPostQueryDto;
import kr.aling.post.bandpost.dto.response.GetBandResponseDto;
import kr.aling.post.bandpost.dto.response.external.GetBandPostUserInfoResponseDto;
import kr.aling.post.bandpost.dto.response.external.GetFileInfoResponseDto;
import kr.aling.post.bandpost.service.BandPostReadService;
import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.common.feign.client.FileFeignClient;
import kr.aling.post.common.feign.client.UserFeignClient;
import kr.aling.post.common.utils.PageUtils;
import kr.aling.post.postfile.dto.response.PostFileQueryDto;
import kr.aling.post.postfile.service.PostFileReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class BandPostFacadeReadService {

    private final BandPostReadService bandPostReadService;
    private final PostFileReadService postFileReadService;

    private final UserFeignClient userFeignClient;
    private final FileFeignClient fileFeignClient;

    public GetBandResponseDto getBandPost(Long postNo) {
        BandPostQueryDto bandPostQueryDto = bandPostReadService.getBandPostInfo(postNo);

        List<Long> fileNoList = bandPostQueryDto.getFiles().stream()
                .map(BandPostQueryDto.AlingFileInfo::getFileNo)
                .collect(Collectors.toList());

        List<GetFileInfoResponseDto> fileInfoResponseDtoList = fileFeignClient.requestFileInfo(fileNoList);

        GetBandPostUserInfoResponseDto getBandPostUserInfoResponseDto = userFeignClient.requestBandPostUserInfo
                (bandPostQueryDto.getBandUserNo());

        BandPostDto bandPostDto = new BandPostDto(bandPostQueryDto, fileInfoResponseDtoList);

        return new GetBandResponseDto(getBandPostUserInfoResponseDto, bandPostDto);
    }

    public PageResponseDto<GetBandResponseDto> getBandPostByBand(Long bandNo, Pageable pageable) {
        Page<BandPostExceptFileQueryDto> bandPostsByBand = bandPostReadService.getBandPostsInfoByBand(bandNo, pageable);

        List<GetBandResponseDto> bandResponseDtoList = new ArrayList<>();
        for (BandPostExceptFileQueryDto bandPostExceptFileQueryDto : bandPostsByBand.getContent()) {
            List<Long> fileNoList =
                    postFileReadService.getPostFileNoList(bandPostExceptFileQueryDto.getPostNo()).stream()
                            .map(PostFileQueryDto::getFileNo)
                            .collect(Collectors.toList());

            List<GetFileInfoResponseDto> fileInfoResponseDtoList = fileFeignClient.requestFileInfo(fileNoList);

            GetBandPostUserInfoResponseDto getBandPostUserInfoResponseDto =
                    userFeignClient.requestBandPostUserInfo(bandPostExceptFileQueryDto.getBandUserNo());

            BandPostDto bandPostDto = new BandPostDto(bandPostExceptFileQueryDto, fileInfoResponseDtoList);
            bandResponseDtoList.add(new GetBandResponseDto(getBandPostUserInfoResponseDto, bandPostDto));
        }

        return PageUtils.convert(
                PageableExecutionUtils.getPage(bandResponseDtoList, pageable, bandPostsByBand::getTotalElements));
    }
}
