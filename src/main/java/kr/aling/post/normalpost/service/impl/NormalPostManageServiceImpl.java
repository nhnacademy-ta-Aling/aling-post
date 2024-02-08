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
    public void createNormalPost(long userNo, CreateNormalPostRequest request) {

        long postNo = postManageService.createPost(request.getCreatePostRequest());

        NormalPost normalPost = NormalPost.builder()
                .userNo(userNo)
                .postNo(postNo)
                .build();

        normalPostManageRepository.save(normalPost);
    }

    @Override
    public void modifyNormalPost(Long postNo, ModifyNormalPostRequest request) {
        postManageService.modifyPost(postNo, request.getModifyPostRequest());
    }

    @Override
    public void deleteById(Long postNo) {
        postManageService.deleteById(postNo);
    }
}
