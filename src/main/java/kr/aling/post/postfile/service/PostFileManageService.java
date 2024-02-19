package kr.aling.post.postfile.service;

import java.util.List;
import kr.aling.post.post.entity.Post;

/**
 * 게시글 파일 생성, 수정, 삭제 Service Interface.
 *
 * @author 박경서
 * @since 1.0
 **/
public interface PostFileManageService {

    /**
     * 게시글 파일 생성.
     *
     * @param post       게시글
     * @param fileNoList 파일 번호 리스트
     */
    void savePostFiles(Post post, List<Long> fileNoList);
}
