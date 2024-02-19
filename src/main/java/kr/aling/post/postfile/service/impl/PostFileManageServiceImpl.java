package kr.aling.post.postfile.service.impl;

import java.util.List;
import kr.aling.post.common.annotation.ManageService;
import kr.aling.post.post.entity.Post;
import kr.aling.post.postfile.entity.PostFile;
import kr.aling.post.postfile.repository.PostFileManageRepository;
import kr.aling.post.postfile.service.PostFileManageService;
import lombok.RequiredArgsConstructor;

/**
 * 게시글 파일 CUD 처리 하는 Service 구현체.
 *
 * @author 박경서
 * @since 1.0
 **/
@ManageService
@RequiredArgsConstructor
public class PostFileManageServiceImpl implements PostFileManageService {

    private final PostFileManageRepository postFileManageRepository;

    /**
     * {@inheritDoc}
     *
     * @param post       게시글
     * @param fileNoList 파일 번호 리스트
     */
    @Override
    public void savePostFiles(Post post, List<Long> fileNoList) {
        fileNoList.stream()
                .map(fileNo -> PostFile.builder()
                        .post(post)
                        .fileNo(fileNo)
                        .build())
                .forEach(postFileManageRepository::save);
    }
}
