package kr.aling.post.normalpost.service.impl;

import kr.aling.post.common.annotation.ManageService;
import kr.aling.post.common.utils.NormalPostUtils;
import kr.aling.post.normalpost.dto.request.CreateNormalPostRequestDto;
import kr.aling.post.normalpost.dto.request.ModifyNormalPostRequestDto;
import kr.aling.post.normalpost.dto.response.CreateNormalPostResponseDto;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.normalpost.repository.NormalPostManageRepository;
import kr.aling.post.normalpost.service.NormalPostManageService;
import kr.aling.post.post.dto.response.CreatePostResponseDto;
import kr.aling.post.post.service.PostManageService;
import lombok.RequiredArgsConstructor;

/**
 * NormalPostManageService 의 구현체입니다. 엔티티의 수정이 발생하는 서비스 레이어 이기 때문에 스프링의 스테레오타입 Service 와 Transaction(readonly = false) 가
 * 적용된 ManageService 커스텀 어노테이션이 적용되어 있습니다.
 *
 * @author : 이성준
 * @see kr.aling.post.common.annotation.ManageService
 * @since 1.0
 */
@ManageService
@RequiredArgsConstructor
public class NormalPostManageServiceImpl implements NormalPostManageService {

    private final NormalPostManageRepository normalPostManageRepository;
    private final PostManageService postManageService;

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateNormalPostResponseDto createNormalPost(Long userNo, CreateNormalPostRequestDto request) {

        CreatePostResponseDto createPostResponse = postManageService.createPost(NormalPostUtils.convert(request));

        NormalPost normalPost = NormalPost.builder()
                .postNo(createPostResponse.getPost().getPostNo())
                .post(createPostResponse.getPost())
                .userNo(userNo)
                .build();

        return new CreateNormalPostResponseDto(normalPostManageRepository.save(normalPost).getPostNo());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyNormalPost(Long postNo, ModifyNormalPostRequestDto request) {
        postManageService.modifyPost(postNo, NormalPostUtils.convert(request));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void safeDeleteById(Long postNo) {
        postManageService.safeDeleteById(postNo);
    }
}
