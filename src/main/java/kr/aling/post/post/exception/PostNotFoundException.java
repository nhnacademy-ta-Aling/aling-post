package kr.aling.post.post.exception;

/**
 * 게시물 엔티티 조회시 존재하지 않을 경우 발생하는 예외 클래스
 *
 * @author : 이성준
 * @since : 1.0
 */

public class PostNotFoundException extends RuntimeException {
    /**
     *
     * @param postNo : 조회 실패한 게시물 번호
     */
    public PostNotFoundException(Long postNo) {
            super("Post Not Found : " + postNo);
    }
}
