package kr.aling.post.post.service;

import kr.aling.post.post.dto.request.CreatePostRequest;
import kr.aling.post.post.dto.request.ModifyPostRequest;

/**
 * 게시믈 생성, 수정, 삭제 서비스 레이어
 *
 * @author : 이성준
 * @since : 1.0
 */

public interface PostManageService {
    /**
     * 게시물 생성
     *
     * @param request : 게시물 생성 데이터
     * @return : 생성된 게시물에 부여된 번호
     */
    long createPost(CreatePostRequest request);

    /**
     * 게시물 수정
     *
     * @param postNo : 수정할 게시물 번호
     * @param request : 게시물 수정에 사용될 데이터
     */
    void modifyPost(Long postNo, ModifyPostRequest request);

    /**
     * 게시물 비공개 처리
     *
     * @param postNo the post no
     */
    void privatePost(Long postNo);

    /**
     * Delete by id.
     *
     * @param postNo the a long
     */
    void deleteById(Long postNo);
}
