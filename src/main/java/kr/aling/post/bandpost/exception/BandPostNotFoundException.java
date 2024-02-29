package kr.aling.post.bandpost.exception;

/**
 * 그룹 게시글 없는 경우 Exception.
 *
 * @author 박경서
 * @since 1.0
 **/
public class BandPostNotFoundException extends RuntimeException {

    public static final String MESSAGE = "Band Post Not Found";

    public BandPostNotFoundException() {
        super(MESSAGE);
    }
}
