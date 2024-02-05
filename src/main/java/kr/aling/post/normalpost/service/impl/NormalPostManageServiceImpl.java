package kr.aling.post.normalpost.service.impl;

import kr.aling.post.common.annotation.ManageService;
import kr.aling.post.normalpost.dto.request.CreateNormalPostRequest;
import kr.aling.post.normalpost.dto.request.ModifyNormalPostRequest;
import kr.aling.post.normalpost.dto.response.ReadNormalPostResponse;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.normalpost.repository.NormalPostManageRepository;
import kr.aling.post.normalpost.service.NormalPostManageService;
import kr.aling.post.normalpost.service.NormalPostReadService;
import kr.aling.post.post.dto.response.ReadPostResponse;
import kr.aling.post.post.service.PostManageService;
import lombok.RequiredArgsConstructor;

@ManageService
@RequiredArgsConstructor
public class NormalPostManageServiceImpl implements NormalPostManageService {

    private final NormalPostManageRepository normalPostManageRepository;

    private final NormalPostReadService normalPostReadService;
    private final PostManageService postManageService;

    @Override
    public void createNormalPost(long userNo, CreateNormalPostRequest request) {

        ReadPostResponse response = postManageService.createPost(request.getCreatePostRequest());

        NormalPost normalPost = NormalPost.builder()
                .userNo(userNo)
                .postNo(response.getPostNo())
                .build();

        normalPostManageRepository.save(normalPost);
    }

    @Override
    public ReadNormalPostResponse modifyNormalPost(Long postNo, ModifyNormalPostRequest request) {
        NormalPost normalPost = normalPostReadService.findById(postNo);

        postManageService.modifyPost(postNo, request.getModifyPostRequest());

        return new ReadNormalPostResponse(normalPost);
    }

    @Override
    public void deleteById(Long postNo, Long userNo) {
        postManageService.deleteById(postNo);
    }
}
