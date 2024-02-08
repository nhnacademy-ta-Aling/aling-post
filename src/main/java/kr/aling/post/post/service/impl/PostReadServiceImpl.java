package kr.aling.post.post.service.impl;

import kr.aling.post.common.annotation.ReadService;
import kr.aling.post.post.dto.response.ReadPostResponse;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.exception.PostNotFoundException;
import kr.aling.post.post.repository.PostReadRepository;
import kr.aling.post.post.service.PostReadService;
import lombok.RequiredArgsConstructor;

@ReadService
@RequiredArgsConstructor
public class PostReadServiceImpl implements PostReadService {

    private final PostReadRepository postReadRepository;

    @Override
    public ReadPostResponse readPostByPostNo(Long postNo) {
        Post post = findById(postNo);

        return new ReadPostResponse(post);
    }

    @Override
    public Post findById(Long postNo) throws PostNotFoundException {
        return postReadRepository.findById(postNo)
                .orElseThrow(() -> new PostNotFoundException(postNo));
    }
}
