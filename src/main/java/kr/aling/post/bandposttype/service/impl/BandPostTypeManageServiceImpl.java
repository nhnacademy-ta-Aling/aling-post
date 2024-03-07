package kr.aling.post.bandposttype.service.impl;

import kr.aling.post.bandpost.repository.BandPostReadRepository;
import kr.aling.post.bandposttype.dto.request.CreateBandPostTypeRequestDto;
import kr.aling.post.bandposttype.dto.request.ModifyBandPostTypeRequestDto;
import kr.aling.post.bandposttype.entity.BandPostType;
import kr.aling.post.bandposttype.exception.BandPostTypeAlreadyExistsException;
import kr.aling.post.bandposttype.exception.BandPostTypeDeniedException;
import kr.aling.post.bandposttype.exception.BandPostTypeNotFoundException;
import kr.aling.post.bandposttype.repository.BandPostTypeManageRepository;
import kr.aling.post.bandposttype.repository.BandPostTypeReadRepository;
import kr.aling.post.bandposttype.service.BandPostTypeManageService;
import kr.aling.post.common.annotation.ManageService;
import lombok.RequiredArgsConstructor;

/**
 * 그룹 게시글 분류 관리 service 구현체.
 *
 * @author 정유진
 * @since 1.0
 **/
@ManageService
@RequiredArgsConstructor
public class BandPostTypeManageServiceImpl implements BandPostTypeManageService {

    private final BandPostTypeManageRepository bandPostTypeManageRepository;
    private final BandPostTypeReadRepository bandPostTypeReadRepository;
    private final BandPostReadRepository bandPostReadRepository;

    /**
     * {@inheritDoc}
     *
     * @param requestDto 게시글 분류 타입 생성에 필요한 정보를 담은 dto
     * @throws BandPostTypeAlreadyExistsException 기본 게시글 분류가 그룹 내에 이미 존재 하는 경우
     */
    @Override
    public void makeBandPostType(CreateBandPostTypeRequestDto requestDto) {
        if (bandPostTypeReadRepository.existsByNameAndBandNo(requestDto.getBandPostTypeName(),
                requestDto.getBandNo())) {
            throw new BandPostTypeAlreadyExistsException();
        }

        bandPostTypeManageRepository.save(
                BandPostType.builder()
                        .bandNo(requestDto.getBandNo())
                        .name(requestDto.getBandPostTypeName())
                        .build());
    }

    /**
     * {@inheritDoc}
     *
     * @param postTypeNo 수정할 그룹 게시글 분류 번호
     * @param requestDto 수정할 그룹 게시글 분류 정보 dto
     * @throws BandPostTypeAlreadyExistsException 그룹 내에 그룹 게시글 분류 명이 이미 존재하는 경우 발생 exception
     * @throws BandPostTypeNotFoundException      그룹 게시글 분류를 찾을 수 없을 때 발생 exception
     */
    @Override
    public void updateBandPostType(Long postTypeNo, ModifyBandPostTypeRequestDto requestDto) {
        if (bandPostTypeReadRepository.existsByNameAndBandNo(requestDto.getBandPostTypeName(),
                requestDto.getBandNo())) {
            throw new BandPostTypeAlreadyExistsException();
        }

        BandPostType bandPostType = bandPostTypeReadRepository.findById(postTypeNo)
                .orElseThrow(BandPostTypeNotFoundException::new);

        bandPostType.updatePostType(requestDto.getBandPostTypeName());
    }

    /**
     * {@inheritDoc}
     *
     * @param postTypeNo 그룹 게시글 분류 번호
     * @throws BandPostTypeNotFoundException 그룹 게시글 분류를 찾을 수 없을 때 발생 exception
     * @throws BandPostTypeDeniedException   그룹 게시글 분류 내 삭제 되지 않은 그룹 게시글이 존재 하는 경우 삭제 불가능 exception
     */
    @Override
    public void deleteBandPostType(Long postTypeNo) {
        BandPostType bandPostType =
                bandPostTypeReadRepository.findById(postTypeNo).orElseThrow(BandPostTypeNotFoundException::new);

        if (bandPostReadRepository.getCountBandPostByBandPostTypeNo(postTypeNo) > 0) {
            throw new BandPostTypeDeniedException();
        }

        bandPostType.deletePostType();
    }
}
