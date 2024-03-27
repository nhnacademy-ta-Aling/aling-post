package kr.aling.post.recruitpost.exception;

/**
 * 채용 공고 관련 접근 제한 exception.
 *
 * @author 정유진
 * @since 1.0
 **/
public class RecruitPostAccessDeniedException extends RuntimeException {
    public static final String MESSAGE = "Access Denied!";

    public RecruitPostAccessDeniedException() {
        super(MESSAGE);
    }
}
