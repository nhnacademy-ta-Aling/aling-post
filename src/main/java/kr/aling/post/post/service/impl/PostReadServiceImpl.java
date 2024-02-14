package kr.aling.post.post.service.impl;

import kr.aling.post.common.annotation.ReadService;
import kr.aling.post.common.utils.NormalPostUtils;
import kr.aling.post.post.dto.response.ReadPostResponseDto;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.exception.PostNotFoundException;
import kr.aling.post.post.repository.PostReadRepository;
import kr.aling.post.post.service.PostReadService;
import lombok.RequiredArgsConstructor;

/**
 * PostReadService 의 구현체입니다.
 * 조회 전용 서비스 레이어 이기 때문에 스프링의 스테레오타입 Service 와 Transaction(readonly = true) 가 적용된 ReadService 커스텀 어노테이션이 적용되어 있습니다.
 *
 * @author : 이성준
 * @since : 1.0
 * @see kr.aling.post.common.annotation.ReadService
 */
@ReadService
@RequiredArgsConstructor
public class PostReadServiceImpl implements PostReadService {

    private final PostReadRepository postReadRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadPostResponseDto readPostByPostNo(Long postNo) {
        Post post = postReadRepository.findById(postNo)
                .orElseThrow(() -> new PostNotFoundException(postNo));

        return NormalPostUtils.convert(post);
    }
}
