package kr.aling.post.postfile.service;

import java.util.List;
import kr.aling.post.postfile.dto.response.PostFileQueryDto;

/**
 * PostFile 조회 용 Service interface.
 *
 * @author 박경서
 * @since 1.0
 **/
public interface PostFileReadService {

    /**
     * 게시글 파일 번호 조회 메서드.
     *
     * @param postNo 게시글 번호
     * @return 게시글 첨부 파일 번호 리스트
     */
    List<PostFileQueryDto> getPostFileNoList(Long postNo);
}
