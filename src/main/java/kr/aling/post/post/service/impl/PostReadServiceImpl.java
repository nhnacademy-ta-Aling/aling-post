package kr.aling.post.post.service.impl;

import feign.FeignException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import kr.aling.post.bandpost.dto.response.external.GetFileInfoResponseDto;
import kr.aling.post.common.annotation.ReadService;
import kr.aling.post.common.feign.client.FileFeignClient;
import kr.aling.post.common.feign.client.UserFeignClient;
import kr.aling.post.common.utils.PostUtils;
import kr.aling.post.post.dto.AdditionalInformationDto;
import kr.aling.post.post.dto.request.WriterRequestDto;
import kr.aling.post.post.dto.response.IsExistsPostResponseDto;
import kr.aling.post.post.dto.response.ReadPostIntegrationDto;
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
    public ReadPostIntegrationDto readPostByPostNo(Long postNo) {
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

        AdditionalInformationDto additional = new AdditionalInformationDto(false);
        Long userNo = getUserNo(post, additional);

        Set<Long> writers = new HashSet<>();
        writers.add(userNo);

        List<ReadWriterResponseDto> response = new ArrayList<>();
        try {
            response = userFeignClient.requestWriterNames(new WriterRequestDto(writers));
        } catch (FeignException e) {
            log.error(e.getMessage());
            response.add(new ReadWriterResponseDto(0L, "Unknown User"));
        }


        return ReadPostIntegrationDto.builder()
                .post(PostUtils.convert(post))
                .writer(response.get(0))
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
     * 게시물의 작성자 번호를 찾는 private 메서드
     *
     * @param post       작성자를 찾을 대상 게시물
     * @param additional
     * @return 게시물 작성자 식별 번호
     * @since : 1.0
     */
    private static Long getUserNo(Post post, AdditionalInformationDto additional) {
        if (Objects.nonNull(post.getBandPost())) {
            additional.isBandPost();
            return post.getBandPost().getBandUserNo();
        }

        return post.getNormalPost().getUserNo();
    }
}
