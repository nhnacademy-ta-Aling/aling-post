package kr.aling.post.bandposttype.exception;

/**
 * 그룹 게시글 분류 찾지 못한 경우의 Exception.
 *
 * @author 박경서
 * @since 1.0
 **/
public class BandPostTypeNotFoundException extends RuntimeException {

    public static final String MESSAGE = "BandPostType Not Found";

    public BandPostTypeNotFoundException() {
        super(MESSAGE);
    }
}
