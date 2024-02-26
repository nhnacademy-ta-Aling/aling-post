package kr.aling.post.bandposttype.exception;

/**
 * 그룹 게시글 분류가 이미 존재할 때 발생하는 exception.
 *
 * @author : 정유진
 * @since : 1.0
 **/
public class BandPostTypeAlreadyExistsException extends RuntimeException {
    private static final String MESSAGE = "Band post type is already exists!";

    public BandPostTypeAlreadyExistsException() {
        super(MESSAGE);
    }
}
