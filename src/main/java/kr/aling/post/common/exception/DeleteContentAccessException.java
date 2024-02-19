package kr.aling.post.common.exception;

/**
 * 삭제된 게시물에 댓글을 달거나, 파일 첨부를 시도하는 것처럼
 * 참조 불가능한 컨텐츠에 대한 요청이 오면 발생하는 예외입니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
public class DeleteContentAccessException extends RuntimeException {

    private static final String MESSAGE = "삭제된 컨텐츠에 대한 시도입니다.";
    public DeleteContentAccessException() {
        super(MESSAGE);
    }
}
