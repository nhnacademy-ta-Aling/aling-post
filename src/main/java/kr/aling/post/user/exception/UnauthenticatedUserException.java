package kr.aling.post.user.exception;

/**
 * @author : 이성준
 * @since : 1.0
 */


public class UnauthenticatedUserException extends RuntimeException {

    public UnauthenticatedUserException(String message) {
        super(message);
    }
}
