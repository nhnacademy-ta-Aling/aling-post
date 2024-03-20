package kr.aling.post.normalpost.service.impl;

import kr.aling.post.common.annotation.ManageService;
import kr.aling.post.common.feign.client.UserFeignClient;
import kr.aling.post.common.utils.PostUtils;
import kr.aling.post.normalpost.dto.request.CreateNormalPostRequestDto;
import kr.aling.post.normalpost.dto.request.ModifyNormalPostRequestDto;
import kr.aling.post.normalpost.dto.response.CreateNormalPostResponseDto;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.normalpost.repository.NormalPostManageRepository;
import kr.aling.post.normalpost.service.NormalPostManageService;
import kr.aling.post.post.dto.response.CreatePostResponseDto;
import kr.aling.post.post.service.PostManageService;
import kr.aling.post.user.exception.UnauthenticatedUserException;
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
    private final UserFeignClient userFeignClient;
    /**
     * {@inheritDoc}
     */
    @Override
    public CreateNormalPostResponseDto createNormalPost(Long userNo, CreateNormalPostRequestDto request) {

        if (Boolean.FALSE.equals(userFeignClient.isExistUser(userNo).getIsExists())) {
            throw new UnauthenticatedUserException("Reply");
        }

        CreatePostResponseDto createPostResponse = postManageService.createPost(PostUtils.convert(request));

        NormalPost normalPost = NormalPost.builder()
                .post(createPostResponse.getPost())
                .userNo(userNo)
                .build();

        return new CreateNormalPostResponseDto(normalPostManageRepository.save(normalPost).getPost().getPostNo());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyNormalPost(Long postNo, Long userNo, ModifyNormalPostRequestDto request) {
        postManageService.modifyPost(postNo, PostUtils.convert(request));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void softDeleteById(Long postNo, Long userNo) {
        postManageService.softDeleteById(postNo, userNo);
    }
}
