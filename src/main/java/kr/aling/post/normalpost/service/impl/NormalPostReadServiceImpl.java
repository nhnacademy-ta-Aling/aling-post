package kr.aling.post.normalpost.service.impl;

import kr.aling.post.common.annotation.ReadService;
import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.common.utils.NormalPostUtils;
import kr.aling.post.common.utils.PageUtils;
import kr.aling.post.normalpost.dto.response.ReadNormalPostResponseDto;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.normalpost.exception.NormalPostNotFoundException;
import kr.aling.post.normalpost.repository.NormalPostReadRepository;
import kr.aling.post.normalpost.service.NormalPostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * NormalPostReadService 의 구현체입니다.
 * 조회 전용 서비스 레이어 이기 때문에 스프링의 스테레오타입 Service 와 Transaction(readonly = true) 가 적용된 ReadService 커스텀 어노테이션이 적용되어 있습니다.
 *
 * @author : 이성준
 * @see kr.aling.post.common.annotation.ReadService
 * @since 1.0
 */
@ReadService
@RequiredArgsConstructor
public class NormalPostReadServiceImpl implements NormalPostReadService {

    private final NormalPostReadRepository normalPostReadRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadNormalPostResponseDto readNormalPostByPostNo(Long postNo) {
        NormalPost normalPost =
                normalPostReadRepository.findById(postNo).orElseThrow(() -> new NormalPostNotFoundException(postNo));

        return NormalPostUtils.convert(normalPost);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponseDto<ReadNormalPostResponseDto> readNormalPostsByUserNo(long userNo, Pageable pageable) {
        Page<ReadNormalPostResponseDto> page = normalPostReadRepository.findAllByUserNo(userNo, pageable);

        return PageUtils.convert(page);
    }
}
