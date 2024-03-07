package kr.aling.post.bandposttype.exception;

/**
 * 그룹 게시글 분류 관련 행위가 거부된 경우 발생 exception.
 *
 * @author 정유진
 * @since 1.0
 **/
public class BandPostTypeDeniedException extends RuntimeException {
    public static final String MESSAGE = "Band post type denied.";

    public BandPostTypeDeniedException() {
        super(MESSAGE);
    }
}
