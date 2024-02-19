package kr.aling.post.reply.exception;

/**
 * 댓글 번호를 조건으로 댓글 엔티티를 조회할 때, 존재하지 않을 경우 발생하는 예외입니다.
 * 
 * @author : 이성준
 * @since : 1.0
 */
public class ReplyNotFoundException extends RuntimeException {

    public static final String MESSAGE = "Reply Not Found : ";

    public ReplyNotFoundException(Long replyNo) {
        super(MESSAGE + replyNo);
    }
}
