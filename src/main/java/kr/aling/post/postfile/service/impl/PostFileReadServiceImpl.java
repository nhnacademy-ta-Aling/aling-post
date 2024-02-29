package kr.aling.post.postfile.service.impl;

import java.util.List;
import kr.aling.post.common.annotation.ReadService;
import kr.aling.post.postfile.dto.response.PostFileQueryDto;
import kr.aling.post.postfile.repository.PostFileReadRepository;
import kr.aling.post.postfile.service.PostFileReadService;
import lombok.RequiredArgsConstructor;

/**
 * PostFile 조회 Service 구현체.
 *
 * @author 박경서
 * @since 1.0
 **/
@ReadService
@RequiredArgsConstructor
public class PostFileReadServiceImpl implements PostFileReadService {

    private final PostFileReadRepository postFileReadRepository;

    /**
     * {@inheritDoc}
     *
     * @param postNo 게시글 번호
     * @return 게시글 첨부 파일 번호 리스트
     */
    @Override
    public List<PostFileQueryDto> getPostFileNoList(Long postNo) {
        return postFileReadRepository.getPostFileByPostNo(postNo);
    }
}
