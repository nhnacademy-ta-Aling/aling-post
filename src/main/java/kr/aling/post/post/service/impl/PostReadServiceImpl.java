package kr.aling.post.post.service.impl;

import feign.FeignException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import kr.aling.post.bandpost.dto.response.external.GetFileInfoResponseDto;
import kr.aling.post.common.annotation.ReadService;
import kr.aling.post.common.feign.client.FileFeignClient;
import kr.aling.post.common.feign.client.UserFeignClient;
import kr.aling.post.common.utils.PostUtils;
import kr.aling.post.post.dto.PostAdditionalInformationDto;
import kr.aling.post.post.dto.response.IsExistsPostResponseDto;
import kr.aling.post.post.dto.response.ReadPostResponseIntegrationDto;
import kr.aling.post.post.dto.response.ReadPostsForScrapResponseDto;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.exception.PostNotFoundException;
import kr.aling.post.post.repository.PostReadRepository;
import kr.aling.post.post.service.PostReadService;
import kr.aling.post.postfile.entity.PostFile;
import kr.aling.post.reply.dto.response.ReadWriterResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PostReadService 의 구현체입니다. 조회 전용 서비스 레이어 이기 때문에 스프링의 스테레오타입 Service 와 Transaction(readonly = true) 가 적용된 ReadService
 * 커스텀 어노테이션이 적용되어 있습니다.
 *
 * @author : 이성준
 * @see kr.aling.post.common.annotation.ReadService
 * @since 1.0
 */
@Slf4j
@ReadService
@RequiredArgsConstructor
public class PostReadServiceImpl implements PostReadService {

    private final PostReadRepository postReadRepository;
    private final UserFeignClient userFeignClient;
    private final FileFeignClient fileFeignClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadPostResponseIntegrationDto readPostByPostNo(Long postNo) {
        Post post = postReadRepository.findByPostNoAndIsDeleteFalse(postNo)
                .orElseThrow(() -> new PostNotFoundException(postNo));

        List<PostFile> postFiles = Objects.nonNull(post.getPostFileList()) ? post.getPostFileList() : new ArrayList<>();

        List<Long> fileNoList = postFiles.stream().map(PostFile::getFileNo).collect(Collectors.toList());

        List<GetFileInfoResponseDto> fileInfoResponseDtoList = new ArrayList<>();

        try {
            fileInfoResponseDtoList = fileFeignClient.requestFileInfo(fileNoList);
        } catch (FeignException e) {
            log.error(e.getMessage());
        }

        PostAdditionalInformationDto additional = new PostAdditionalInformationDto();
        Long userNo = getUserNo(post, additional);

        ReadWriterResponseDto writerResponseDto;
        try {
            writerResponseDto = userFeignClient.requestBandPostUserInfo(userNo);
        } catch (FeignException e) {
            log.error(e.getMessage());
            writerResponseDto = new ReadWriterResponseDto(1L, "Unknown User", null);
        }


        return ReadPostResponseIntegrationDto.builder()
                .post(PostUtils.convert(post))
                .writer(writerResponseDto)
                .additional(additional)
                .file(fileInfoResponseDtoList)
                .build();
    }

    /**
     * {@inheritDoc}
     *
     * @param postNo 조회할 게시물의 번호
     * @return 게시물의 존재 여부
     */
    @Override
    public IsExistsPostResponseDto isExistsPost(Long postNo) {
        return new IsExistsPostResponseDto(postReadRepository.existsById(postNo));
    }

    /**
     * {@inheritDoc}
     *
     * @param postNos 조회할 게시물 번호 리스트
     * @return 스크랩 조회에 필요한 게시물 내용 리스트를 담은 Dto
     */
    @Override
    public ReadPostsForScrapResponseDto getPostsForScrap(List<Long> postNos) {
        return new ReadPostsForScrapResponseDto(postReadRepository.getPostInfoForScrap(postNos));
    }

    /**
     * 게시물의 작성자 번호를 찾고, 그룹 게시물 일 경우 추가 정보를 할당하는 메서드입니다.
     *
     * @param post       작성자를 찾을 대상 게시물
     * @param additional 게시물의 추가 정보(그룹 게시물일 경우 etc...)
     * @return 게시물 작성자 식별 번호
     * @since : 1.0
     */
    private static Long getUserNo(Post post, PostAdditionalInformationDto additional) {
        if (Objects.nonNull(post.getBandPost())) {
            additional.setBandNo(post.getBandPost().getBandNo());
            return post.getBandPost().getBandUserNo();
        }

        return post.getNormalPost().getUserNo();
    }
}
