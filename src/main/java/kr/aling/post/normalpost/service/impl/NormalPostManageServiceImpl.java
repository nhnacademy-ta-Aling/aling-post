package kr.aling.post.normalpost.service.impl;

import kr.aling.post.common.annotation.ManageService;
import kr.aling.post.normalpost.dto.request.CreateNormalPostRequest;
import kr.aling.post.normalpost.dto.request.ModifyNormalPostRequest;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.normalpost.repository.NormalPostManageRepository;
import kr.aling.post.normalpost.service.NormalPostManageService;
import kr.aling.post.post.service.PostManageService;
import lombok.RequiredArgsConstructor;

@ManageService
@RequiredArgsConstructor
public class NormalPostManageServiceImpl implements NormalPostManageService {

    private final NormalPostManageRepository normalPostManageRepository;

    private final PostManageService postManageService;

    @Override
    public Long createNormalPost(Long userNo, CreateNormalPostRequest request) {

        Long postNo = postManageService.createPost(request.createPostRequest());

        NormalPost normalPost = NormalPost.builder()
                .userNo(userNo)
                .postNo(postNo)
                .build();

        return normalPostManageRepository.save(normalPost).getPostNo();
    }

    @Override
    public void modifyNormalPost(Long postNo, ModifyNormalPostRequest request) {
        postManageService.modifyPost(postNo, request.modifyPostRequest());
    }

    @Override
    public void deleteById(Long postNo) {
        postManageService.deleteById(postNo);
        normalPostManageRepository.deleteById(postNo);
    }
}
