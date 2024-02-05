package kr.aling.post.post.service.impl;

import java.util.Optional;
import kr.aling.post.common.annotation.ManageService;
import kr.aling.post.post.dto.request.CreatePostRequest;
import kr.aling.post.post.dto.request.ModifyPostRequest;
import kr.aling.post.post.dto.response.ReadPostResponse;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.repository.PostManageRepository;
import kr.aling.post.post.service.PostManageService;
import kr.aling.post.post.service.PostReadService;
import lombok.RequiredArgsConstructor;

@ManageService
@RequiredArgsConstructor
public class PostManageServiceImpl implements PostManageService {

    private final PostManageRepository postManageRepository;
    private final PostReadService postReadService;

    @Override
    public ReadPostResponse createPost(CreatePostRequest request) {
        Post post = Post.builder()
                .content(request.getContent())
                .isOpen(request.isOpen())
                .build();

        post = postManageRepository.save(post);
        return new ReadPostResponse(post);
    }

    @Override
    public ReadPostResponse modifyPost(Long postNo, ModifyPostRequest request) {
        Post post = postReadService.findById(postNo);

        post.modifyContent(request.getContent());

        if (!request.isOpen()) {
            post.makePrivate();
        }

        return new ReadPostResponse(post);
    }

    @Override
    public void deleteById(Long postNo) {
        Post post = postReadService.findById(postNo);

        post.safeDelete();
    }

    @Override
    public void privatePost(Long postNo) {
        Optional<Post> postOptional = postManageRepository.findById(postNo);

        postOptional.ifPresent(Post::makePrivate);
    }
}
