package kr.aling.post.bandposttype.service.impl;

import kr.aling.post.bandposttype.dto.request.CreateBandPostTypeDefaultRequestDto;
import kr.aling.post.bandposttype.dto.request.CreateBandPostTypeRequestDto;
import kr.aling.post.bandposttype.entity.BandPostType;
import kr.aling.post.bandposttype.exception.BandPostTypeAlreadyExistsException;
import kr.aling.post.bandposttype.repository.BandPostTypeManageRepository;
import kr.aling.post.bandposttype.repository.BandPostTypeReadRepository;
import kr.aling.post.bandposttype.service.BandPostTypeManageService;
import kr.aling.post.common.annotation.ManageService;
import kr.aling.post.common.enums.BandPostTypeEnum;
import lombok.RequiredArgsConstructor;

/**
 * 그룹 게시글 분류 관리 service 구현체.
 *
 * @author : 정유진
 * @since : 1.0
 **/
@ManageService
@RequiredArgsConstructor
public class BandPostTypeManageServiceImpl implements BandPostTypeManageService {

    private final BandPostTypeManageRepository bandPostTypeManageRepository;
    private final BandPostTypeReadRepository bandPostTypeReadRepository;

    /**
     * {@inheritDoc}
     *
     * @param requestDto 기본 게시글 분류 타입 생성에 필요한 정보를 담은 dto
     * @throws BandPostTypeAlreadyExistsException 기본 게시글 분류가 그룹 내에 이미 존재 하는 경우
     */
    public void makeDefaultType(CreateBandPostTypeDefaultRequestDto requestDto) {
        if (bandPostTypeReadRepository.existsByNameAndBandNo(BandPostTypeEnum.DEFAULT.getBandPostTypeName(),
                requestDto.getBandNo())) {
            throw new BandPostTypeAlreadyExistsException();
        }

        bandPostTypeManageRepository.save(
                BandPostType.builder()
                        .bandNo(requestDto.getBandNo())
                        .name("default")
                        .build());
    }

    /**
     * {@inheritDoc}
     *
     * @param requestDto 기본 게시글 분류 타입 생성에 필요한 정보를 담은 dto
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
}
