package kr.aling.post.normalpost.service.impl;

import kr.aling.post.common.annotation.ReadService;
import kr.aling.post.normalpost.dto.response.ReadNormalPostResponse;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.normalpost.exception.NormalPostNotFoundException;
import kr.aling.post.normalpost.repository.NormalPostReadRepository;
import kr.aling.post.normalpost.service.NormalPostReadService;
import kr.aling.post.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ReadService
@RequiredArgsConstructor
public class NormalPostReadServiceImpl implements NormalPostReadService {

    private final NormalPostReadRepository normalPostReadRepository;


    @Override
    public ReadNormalPostResponse readNormalPostByPostNo(Long postNo) {
        NormalPost normalPost = findById(postNo);

        return new ReadNormalPostResponse(normalPost);
    }

    @Override
    public NormalPost findById(Long postNo) throws PostNotFoundException {
        return normalPostReadRepository.findById(postNo)
                .orElseThrow(() -> new NormalPostNotFoundException(postNo));
    }

    @Override
    public Page<ReadNormalPostResponse> readNormalPostsByUserNo(long userNo, Pageable pageable) {
        return normalPostReadRepository.findAllByUserNo(userNo, pageable);
    }
}
