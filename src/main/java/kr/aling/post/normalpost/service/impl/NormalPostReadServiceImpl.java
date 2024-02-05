package kr.aling.post.normalpost.service.impl;

import java.util.List;
import java.util.Optional;
import kr.aling.post.common.annotation.ReadService;
import kr.aling.post.normalpost.dto.response.ReadNormalPostResponse;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.normalpost.exception.NormalPostNotFoundException;
import kr.aling.post.normalpost.repository.NormalPostReadRepository;
import kr.aling.post.normalpost.service.NormalPostReadService;
import kr.aling.post.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;

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
        Optional<NormalPost> normalPostOptional = normalPostReadRepository.findById(postNo);

        if (normalPostOptional.isPresent()) {
            return normalPostOptional.get();
        }

        throw new NormalPostNotFoundException(postNo);
    }

    @Override
    public List<ReadNormalPostResponse> readNormalPostsByUserNo(long userNo) {
        return normalPostReadRepository.findAllByUserNo(userNo);
    }
}
