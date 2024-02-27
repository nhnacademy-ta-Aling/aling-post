package kr.aling.post.postfile.service.impl;

import java.util.List;
import kr.aling.post.common.annotation.ReadService;
import kr.aling.post.postfile.dto.response.PostFileQueryDto;
import kr.aling.post.postfile.repository.PostFileReadRepository;
import kr.aling.post.postfile.service.PostFileReadService;
import lombok.RequiredArgsConstructor;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
@ReadService
@RequiredArgsConstructor
public class PostFileReadServiceImpl implements PostFileReadService {

    private final PostFileReadRepository postFileReadRepository;

    @Override
    public List<PostFileQueryDto> getPostFileNoList(Long postNo) {
        return postFileReadRepository.getPostFileByPostNo(postNo);
    }
}
