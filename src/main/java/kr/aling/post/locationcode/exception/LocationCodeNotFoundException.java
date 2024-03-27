package kr.aling.post.locationcode.exception;

/**
 * 지역 코드를 찾을 수 없을 경우 발생 exception.
 *
 * @author 정유진
 * @since 1.0
 **/
public class LocationCodeNotFoundException extends RuntimeException {
    public static final String MESSAGE = "LocationCode is not found!";

    public LocationCodeNotFoundException() {
        super(MESSAGE);
    }
}
