package kr.aling.post.bandpost.service.impl;

import kr.aling.post.bandpost.dto.request.CreateBandPostRequestDto;
import kr.aling.post.bandpost.dto.request.ModifyBandPostRequestDto;
import kr.aling.post.bandpost.entity.BandPost;
import kr.aling.post.bandpost.exception.BandPostNotFoundException;
import kr.aling.post.bandpost.repository.BandPostManageRepository;
import kr.aling.post.bandpost.service.BandPostManageService;
import kr.aling.post.bandposttype.entity.BandPostType;
import kr.aling.post.bandposttype.exception.BandPostTypeNotFoundException;
import kr.aling.post.bandposttype.repository.BandPostTypeReadRepository;
import kr.aling.post.common.annotation.ManageService;
import kr.aling.post.post.dto.response.CreatePostResponseDto;
import lombok.RequiredArgsConstructor;

/**
 * 그룹 게시글 생성, 수정 삭제 Service 구현체.
 *
 * @author 박경서
 * @since 1.0
 **/
@ManageService
@RequiredArgsConstructor
public class BandPostManageServiceImpl implements BandPostManageService {

    private final BandPostManageRepository bandPostManageRepository;
    private final BandPostTypeReadRepository bandPostTypeReadRepository;

    /**
     * {@inheritDoc}
     *
     * @param createPostResponseDto    게시글 생성 응답 Dto
     * @param createBandPostRequestDto 그룹 게시글 생성 요청 Dto
     * @param baneUserNo               그룹 회원 번호
     * @param alingUserNo              회원 번호
     */
    @Override
    public void createBandPost(CreatePostResponseDto createPostResponseDto,
            CreateBandPostRequestDto createBandPostRequestDto, Long baneUserNo, Long alingUserNo) {
        BandPostType bandPostType = bandPostTypeReadRepository.findById(createBandPostRequestDto.getBandPostTypeNo())
                .orElseThrow(BandPostTypeNotFoundException::new);

        BandPost bandPost = BandPost.builder()
                .postNo(createPostResponseDto.getPost().getPostNo())
                .post(createPostResponseDto.getPost())
                .bandNo(createBandPostRequestDto.getBandNo())
                .bandPostType(bandPostType)
                .bandUserNo(baneUserNo)
                .title(createBandPostRequestDto.getBandPostTitle())
                .alingUserNo(alingUserNo)
                .build();

        bandPostManageRepository.save(bandPost);
    }

    /**
     * {@inheritDoc}
     *
     * @param postNo                   게시글 번호
     * @param modifyBandPostRequestDto 그룹 게시글 수정 요청 Dto
     */
    @Override
    public void modifyBandPost(Long postNo, ModifyBandPostRequestDto modifyBandPostRequestDto) {
        BandPost bandPost = bandPostManageRepository.findById(postNo).orElseThrow(BandPostNotFoundException::new);
        BandPostType bandPostType = bandPostTypeReadRepository.findById(modifyBandPostRequestDto.getBandPostTypeNo())
                .orElseThrow(BandPostTypeNotFoundException::new);

        bandPost.modifyBandPost(modifyBandPostRequestDto.getBandPostTitle(), bandPostType);
    }
}
