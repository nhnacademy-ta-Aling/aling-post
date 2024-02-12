package kr.aling.post.post.service.impl;

import kr.aling.post.common.annotation.ManageService;
import kr.aling.post.post.dto.request.CreatePostRequest;
import kr.aling.post.post.dto.request.ModifyPostRequest;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.repository.PostManageRepository;
import kr.aling.post.post.service.PostManageService;
import kr.aling.post.post.service.PostReadService;
import lombok.RequiredArgsConstructor;

/**
 * The type Post manage service.
 */
@ManageService
@RequiredArgsConstructor
public class PostManageServiceImpl implements PostManageService {

    private final PostManageRepository postManageRepository;
    private final PostReadService postReadService;

    @Override
    public long createPost(CreatePostRequest request) {
        Post post = Post.builder()
                .content(request.getContent())
                .isOpen(request.getIsOpen())
                .build();

        return postManageRepository.save(post).getPostNo();
    }

    @Override
    public void modifyPost(Long postNo, ModifyPostRequest request) {
        Post post = postReadService.findById(postNo);

        post.modifyContent(request.getContent());

        if (!post.isOpen().equals(request.getIsOpen())) {
            if (Boolean.TRUE.equals(request.getIsOpen())) {
                post.makePublic();
            } else {
                post.makePrivate();
            }
        }
    }

    @Override
    public void deleteById(Long postNo) {
        postReadService.findById(postNo).safeDelete();
    }

    @Override
    public void privatePost(Long postNo) {
        postReadService.findById(postNo).makePrivate();
    }
}
