package kr.aling.post.recruitbranchcode.exception;

/**
 * 채용 분야를 찾을 수 없을 경우 발생 exception.
 *
 * @author 정유진
 * @since 1.0
 **/
public class RecruitBranchCodeNotFoundException extends RuntimeException {
    public static final String MESSAGE = "Recruit branch code is not found!";

    public RecruitBranchCodeNotFoundException() {
        super(MESSAGE);
    }
}
