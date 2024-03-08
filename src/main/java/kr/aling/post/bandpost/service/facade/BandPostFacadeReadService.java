package kr.aling.post.bandpost.service.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import kr.aling.post.bandpost.dto.response.BandPostDto;
import kr.aling.post.bandpost.dto.response.BandPostExceptFileQueryDto;
import kr.aling.post.bandpost.dto.response.BandPostQueryDto;
import kr.aling.post.bandpost.dto.response.GetBandResponseDto;
import kr.aling.post.bandpost.dto.response.external.GetFileInfoResponseDto;
import kr.aling.post.bandpost.service.BandPostReadService;
import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.common.feign.client.FileFeignClient;
import kr.aling.post.common.feign.client.UserFeignClient;
import kr.aling.post.common.utils.PageUtils;
import kr.aling.post.postfile.dto.response.PostFileQueryDto;
import kr.aling.post.postfile.service.PostFileReadService;
import kr.aling.post.reply.dto.response.ReadUserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 그룹 게시글 조회 Facade Service.
 *
 * @author 박경서
 * @since 1.0
 **/
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BandPostFacadeReadService {

    private final BandPostReadService bandPostReadService;
    private final PostFileReadService postFileReadService;

    private final UserFeignClient userFeignClient;
    private final FileFeignClient fileFeignClient;

    /**
     * 그룹 게시글 단건 조회 Facade 메서드. <br>
     * 다음 로직을 순차적 수행. <br>
     * <ol>
     *     <li>post DB 에서 게시글 정보 (파일 번호 리스트 포함) 만 쿼리를 통해 가져옴</li>
     *     <li>쿼리를 통해 가져온 첨부 파일 번호 리스트 가지고 File 서버에 조회 API 호출</li>
     *     <li>첫 쿼리 에서 가져온 유저 번호를 가지고 User 서버에 조회 API 호출</li>
     *     <li>위 세 단계 데이터 종합 하여 응답 Dto 생성</li>
     * </ol>
     *
     * @param postNo 게시글 번호
     * @return 모든 정보가 담긴 게시글 응답 Dto
     */
    public GetBandResponseDto getBandPost(Long postNo) {
        BandPostQueryDto bandPostQueryDto = bandPostReadService.getBandPostInfo(postNo);

        List<Long> fileNoList = bandPostQueryDto.getFiles().stream()
                .map(BandPostQueryDto.AlingFileInfo::getFileNo)
                .collect(Collectors.toList());

        List<GetFileInfoResponseDto> fileInfoResponseDtoList = fileFeignClient.requestFileInfo(fileNoList);

        ReadUserInfoResponseDto getBandPostUserInfoResponseDto =
                userFeignClient.requestBandPostUserInfo(bandPostQueryDto.getBandUserNo());

        BandPostDto bandPostDto = new BandPostDto(bandPostQueryDto, fileInfoResponseDtoList);

        return new GetBandResponseDto(getBandPostUserInfoResponseDto, bandPostDto);
    }

    /**
     * 그룹 게시글 페이징 조회 Facade 메서드. <Br>
     * 다음 로직을 순차적 수행. <br>
     * <ol>
     *     <li>post DB 에서 게시글 정보 만 쿼리를 통해 가져옴 (이때, 파일 번호 리스트 가져 오지 않음)</li>
     *     <li>쿼리를 통해 가져온 파일 번호를 가지고 post DB 에 파일 번호 리스틑 쿼리를 통해 가져옴</li>
     *     <li>2 단계 에서 가져온 번호 리스트 가지고 File 서버에 조회 API 호출</li>
     *     <li>유저 번호를 가지고 User 서버에 조회 API 호출</li>
     *     <li>위 과정을 거쳐 종합 응답 Dto 생성</li>
     * </ol>
     *
     * @param bandNo   그룹 번호
     * @param pageable 페이징
     * @return 페이징 처리 된 게시글 응답 Dto
     */
    public PageResponseDto<GetBandResponseDto> getBandPostByBand(Long bandNo, Pageable pageable) {
        Page<BandPostExceptFileQueryDto> bandPostsByBand = bandPostReadService.getBandPostsInfoByBand(bandNo, pageable);

        List<GetBandResponseDto> bandResponseDtoList = new ArrayList<>();
        for (BandPostExceptFileQueryDto bandPostExceptFileQueryDto : bandPostsByBand.getContent()) {
            List<Long> fileNoList =
                    postFileReadService.getPostFileNoList(bandPostExceptFileQueryDto.getPostNo()).stream()
                            .map(PostFileQueryDto::getFileNo)
                            .collect(Collectors.toList());

            List<GetFileInfoResponseDto> fileInfoResponseDtoList = fileFeignClient.requestFileInfo(fileNoList);

            ReadUserInfoResponseDto getBandPostUserInfoResponseDto =
                    userFeignClient.requestBandPostUserInfo(bandPostExceptFileQueryDto.getBandUserNo());

            BandPostDto bandPostDto = new BandPostDto(bandPostExceptFileQueryDto, fileInfoResponseDtoList);
            bandResponseDtoList.add(new GetBandResponseDto(getBandPostUserInfoResponseDto, bandPostDto));
        }

        return PageUtils.convert(
                PageableExecutionUtils.getPage(bandResponseDtoList, pageable, bandPostsByBand::getTotalElements));
    }
}
