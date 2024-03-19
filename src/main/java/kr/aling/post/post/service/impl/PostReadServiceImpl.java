package kr.aling.post.post.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import kr.aling.post.bandpost.dto.response.external.GetFileInfoResponseDto;
import kr.aling.post.common.annotation.ReadService;
import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.common.utils.PageUtils;
import kr.aling.post.common.utils.PostUtils;
import kr.aling.post.post.dto.request.ReadAuthorInfoRequestDto;
import kr.aling.post.post.dto.response.BandPostResponseDto;
import kr.aling.post.post.dto.response.IsExistsPostResponseDto;
import kr.aling.post.post.dto.response.NormalPostResponseDto;
import kr.aling.post.post.dto.response.PostAdditionalInformationDto;
import kr.aling.post.post.dto.response.ReadPostResponseIntegrationDto;
import kr.aling.post.post.dto.response.ReadPostsForScrapResponseDto;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.exception.PostNotFoundException;
import kr.aling.post.post.repository.PostReadRepository;
import kr.aling.post.post.service.PostReadService;
import kr.aling.post.postfile.adaptor.PostFileAdaptor;
import kr.aling.post.postfile.dto.request.ReadPostFileRequestDto;
import kr.aling.post.postfile.dto.response.PostFileQueryDto;
import kr.aling.post.postfile.entity.PostFile;
import kr.aling.post.postfile.repository.PostFileReadRepository;
import kr.aling.post.reply.dto.response.ReadUserInfoResponseDto;
import kr.aling.post.user.adaptor.AuthorInformationAdaptor;
import kr.aling.post.user.dto.request.ReadPostAuthorInfoRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.ObjectUtils;

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
    private final PostFileReadRepository postFileReadRepository;

    private final PostFileAdaptor postFileAdaptor;
    private final AuthorInformationAdaptor authorInformationAdaptor;

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadPostResponseIntegrationDto readPostByPostNo(Long postNo) {
        Post post = postReadRepository.findByPostNoAndIsDeleteFalseAndIsOpenTrue(postNo)
                .orElseThrow(() -> new PostNotFoundException(postNo));

        List<PostFile> postFiles = Objects.nonNull(post.getPostFileList()) ? post.getPostFileList() : new ArrayList<>();

        List<Long> fileNoList = postFiles.stream().map(PostFile::getFileNo).collect(Collectors.toList());

        List<GetFileInfoResponseDto> fileInfoResponseDtoList = postFileAdaptor.readPostFiles(fileNoList);

        ReadPostAuthorInfoRequestDto readPostAuthorInfoRequest = getAuthor(post);

        ReadUserInfoResponseDto writer;

        if (readPostAuthorInfoRequest.isBandPost()) {
            writer = authorInformationAdaptor.readBandPostAuthorInfo(
                    readPostAuthorInfoRequest.getAuthorInfo().getUserNo());
        } else {
            writer = authorInformationAdaptor.readNormalPostAuthorInfo(
                    readPostAuthorInfoRequest.getAuthorInfo().getUserNo());
        }

        return getIntegrationDto(post, fileInfoResponseDtoList, writer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponseDto<ReadPostResponseIntegrationDto> readPostsThatIsOpen(Pageable pageable) {
        Page<Post> page = postReadRepository.getAllByIsDeleteFalseAndIsOpenTrue(pageable);
        List<Post> posts = page.getContent();

        List<ReadPostFileRequestDto> filesRequests = new ArrayList<>();
        Set<ReadPostAuthorInfoRequestDto> authorRequests = new HashSet<>();

        posts.forEach(
                post -> {
                    if (!ObjectUtils.isEmpty(post.getPostFileList())) {
                        filesRequests.add(
                                new ReadPostFileRequestDto(post.getPostNo(),
                                        post.getPostFileList().stream().map(PostFile::getFileNo)
                                                .collect(Collectors.toList())));
                        authorRequests.add(getAuthor(post));
                    }
                }
        );

        Map<Long, List<GetFileInfoResponseDto>> postFileMap = postFileAdaptor.readPostsFiles(filesRequests);
        Map<Long, ReadUserInfoResponseDto> authorInfoMap = authorInformationAdaptor.readPostAuthorInfo(authorRequests);

        List<ReadPostResponseIntegrationDto> pageContent = new ArrayList<>();

        posts.forEach(
                post -> pageContent.add(
                        getIntegrationDto(post, postFileMap.get(post.getPostNo()), authorInfoMap.get(post.getPostNo())))
        );

        return PageUtils.convert(
                PageableExecutionUtils.getPage(pageContent, page.getPageable(), page.getPageable()::getOffset));
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
     * {@inheritDoc}
     *
     * @param userNo   회원 번호
     * @param pageable 페이징
     * @return 회원별 페이징 일반 게시글 응답 Dto
     */
    @Override
    public PageResponseDto<ReadPostResponseIntegrationDto> getNormalPostsByUserNo(Long userNo, Pageable pageable) {
        Page<NormalPostResponseDto> page = postReadRepository.getNormalPostsByUserNo(userNo, pageable);

        List<ReadPostFileRequestDto> fileRequestList = new ArrayList<>();
        for (NormalPostResponseDto normalPostResponseDto : page.getContent()) {
            List<Long> fileNoList = postFileReadRepository.getPostFileByPostNo(normalPostResponseDto.getPostNo())
                    .stream().map(PostFileQueryDto::getFileNo)
                    .collect(Collectors.toList());

            fileRequestList.add(ReadPostFileRequestDto.builder()
                    .postNo(normalPostResponseDto.getPostNo())
                    .fileNoList(fileNoList).build());
        }

        Map<Long, List<GetFileInfoResponseDto>> postFileMap = postFileAdaptor.readPostsFiles(fileRequestList);
        ReadUserInfoResponseDto readUserInfoResponseDto = authorInformationAdaptor.readNormalPostAuthorInfo(userNo);

        List<ReadPostResponseIntegrationDto> content = new ArrayList<>();
        page.forEach(
                post -> content.add(
                        convertToNormalPostIntegrationDto(post, postFileMap.get(post.getPostNo()),
                                readUserInfoResponseDto))
        );

        return PageUtils.convert(
                PageableExecutionUtils.getPage(content, pageable, page::getTotalElements));
    }

    /**
     * {@inheritDoc}
     *
     * @param userNo   회원 번호
     * @param pageable 페이징
     * @return 회원별 페이징 그룹 게시글 응답 Dto
     */
    @Override
    public PageResponseDto<ReadPostResponseIntegrationDto> getBandPostsByUserNo(Long userNo, Pageable pageable) {
        Page<BandPostResponseDto> page = postReadRepository.getBandPostsByUserNo(userNo, pageable);

        List<ReadPostFileRequestDto> fileRequestList = new ArrayList<>();
        for (BandPostResponseDto bandPostResponseDto : page.getContent()) {
            List<Long> fileNoList = postFileReadRepository.getPostFileByPostNo(bandPostResponseDto.getPostNo())
                    .stream().map(PostFileQueryDto::getFileNo)
                    .collect(Collectors.toList());

            fileRequestList.add(ReadPostFileRequestDto.builder()
                    .postNo(bandPostResponseDto.getPostNo())
                    .fileNoList(fileNoList).build());
        }

        Map<Long, List<GetFileInfoResponseDto>> postFileMap = postFileAdaptor.readPostsFiles(fileRequestList);
        ReadUserInfoResponseDto readUserInfoResponseDto = authorInformationAdaptor.readNormalPostAuthorInfo(userNo);

        List<ReadPostResponseIntegrationDto> content = new ArrayList<>();
        page.forEach(
                bandPost -> content.add(convertToBandPostIntegrationDto(bandPost, postFileMap.get(bandPost.getPostNo()),
                        readUserInfoResponseDto))
        );

        return PageUtils.convert(
                PageableExecutionUtils.getPage(content, pageable, page::getTotalElements));
    }

    /**
     * 게시물 정보 통합 응답 객체를 만드는 private 메서드 입니다.
     *
     * @param post       대상 게시물
     * @param postFiles  게시물에 첨부된 파일 목록
     * @param authorInfo 작성자 정보
     * @return 정보가 통합된 응답 객체입니다.
     * @since : 1.0
     */
    private ReadPostResponseIntegrationDto getIntegrationDto(Post post,
                                                             List<GetFileInfoResponseDto> postFiles,
                                                             ReadUserInfoResponseDto authorInfo) {

        PostAdditionalInformationDto additional = null;

        if (Objects.nonNull(post.getBandPost())) {
            additional = PostAdditionalInformationDto.builder()
                    .isOpen(post.getIsOpen())
                    .bandNo(post.getBandPost().getBandNo())
                    .title(post.getBandPost().getTitle())
                    .postTypeNo(post.getBandPost().getBandPostType().getBandPostTypeNo())
                    .postTypeName(post.getBandPost().getBandPostType().getName())
                    .build();
        }

        return ReadPostResponseIntegrationDto.builder()
                .post(PostUtils.convert(post))
                .writer(authorInfo)
                .files(postFiles)
                .additional(additional)
                .build();
    }

    private ReadPostResponseIntegrationDto convertToNormalPostIntegrationDto(
            NormalPostResponseDto normalPostResponseDto, List<GetFileInfoResponseDto> postFiles,
            ReadUserInfoResponseDto readUserInfoResponseDto) {
        return ReadPostResponseIntegrationDto.builder()
                .post(PostUtils.convert(normalPostResponseDto))
                .writer(readUserInfoResponseDto)
                .files(postFiles)
                .build();
    }

    private ReadPostResponseIntegrationDto convertToBandPostIntegrationDto(BandPostResponseDto bandPostResponseDto,
                                                                           List<GetFileInfoResponseDto> postFiles,
                                                                           ReadUserInfoResponseDto readUserInfoResponseDto) {

        PostAdditionalInformationDto postAdditionalInformationDto = PostAdditionalInformationDto.builder()
                .isOpen(bandPostResponseDto.getIsOpen())
                .bandNo(bandPostResponseDto.getBandNo())
                .title(bandPostResponseDto.getTitle())
                .postTypeNo(bandPostResponseDto.getPostTypeNo())
                .postTypeName(bandPostResponseDto.getPostTypeName())
                .build();

        return ReadPostResponseIntegrationDto.builder()
                .post(PostUtils.convert(bandPostResponseDto))
                .writer(readUserInfoResponseDto)
                .files(postFiles)
                .additional(postAdditionalInformationDto)
                .build();
    }

    /**
     * 게시물의 작성자를 찾는 private 메서드입니다.
     *
     * @param post 작성자를 찾을 대상 게시물
     * @return 작성자 정보 요청 객체
     * @author : 이성준
     * @since : 1.0
     */
    private ReadPostAuthorInfoRequestDto getAuthor(Post post) {
        ReadPostAuthorInfoRequestDto authorInfoRequestDto;

        if (Objects.nonNull(post.getBandPost())) {
            authorInfoRequestDto =
                    new ReadPostAuthorInfoRequestDto(post.getPostNo(), true,
                            new ReadAuthorInfoRequestDto(post.getBandPost().getBandUserNo()));
        } else {
            authorInfoRequestDto =
                    new ReadPostAuthorInfoRequestDto(post.getPostNo(), false,
                            new ReadAuthorInfoRequestDto(post.getNormalPost().getUserNo()));
        }

        return authorInfoRequestDto;
    }
}
