package kr.aling.post.common.advice;

import kr.aling.post.bandpost.exception.BandPostNotFoundException;
import kr.aling.post.bandposttype.exception.BandPostTypeNotFoundException;
import kr.aling.post.common.dto.ErrorResponseDto;
import kr.aling.post.common.utils.ErrorResponseUtils;
import kr.aling.post.normalpost.exception.NormalPostNotFoundException;
import kr.aling.post.post.exception.PostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Rest Api 전역 예외 처리.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Slf4j
@RestControllerAdvice
public class AlingPostControllerAdvice {

    /**
     * 400 - BadRequest 상태 코드를 포함하는 예외 중 유효값 검사 예외 핸들링 메서드.
     *
     * @param exception Rest Controller 에서 발생한 예외 중 Exception Handler 에 등록된 예외
     * @return 상태 코드와 예외에 대한 메시지를 담은 Response Entity
     * @author : 이성준
     * @since : 1.0
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException exception) {
        loggingError(HttpStatus.BAD_REQUEST, exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseUtils.makeResponse(exception));
    }

    /**
     * 404 - Not Found 상태 코드를 포함 하는 예외 핸들링 메서드.
     *
     * @param exception Rest Controller 에서 발생한 예외 중 Exception Handler 에 등록된 예외
     * @return 상태 코드와 예외에 대한 메시지를 담은 Response Entity
     * @author : 이성준
     * @since : 1.0
     */
    @ExceptionHandler({PostNotFoundException.class, NormalPostNotFoundException.class,
            BandPostTypeNotFoundException.class, BandPostNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> handleNotFoundExceptions(RuntimeException exception) {
        loggingError(HttpStatus.NOT_FOUND, exception);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseUtils.makeResponse(exception));
    }


    /**
     * 406 - Not Acceptable 상태 코드를 포함하는 예외 핸들링 메서드 <Br>
     * Content Negotiation 관련 예외를 처리합니다.
     *
     * @param exception Rest Controller 에서 발생한 예외 중 Exception Handler 에 등록된 예외
     * @return 상태 코드와 예외에 대한 메시지를 담은 Response Entity
     * @author : 이성준
     * @since : 1.0
     */
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity<ErrorResponseDto> handleNotAcceptableExceptions(HttpMediaTypeException exception) {
        loggingError(HttpStatus.NOT_ACCEPTABLE, exception);

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(ErrorResponseUtils.makeResponse(exception));
    }

    /**
     * 415 - Unsupported Media Type 상태 코드를 포함하는 예외 핸들링 메서드.
     *
     * @param exception Rest Controller 에서 발생한 예외 중 Exception Handler 에 등록된 예외
     * @return 상태 코드와 예외에 대한 메시지를 담은 Response Entity
     * @author : 이성준
     * @since : 1.0
     */
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<ErrorResponseDto> handleUnsupportedMediaTypeExceptions(HttpMediaTypeException exception) {
        loggingError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception);

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(ErrorResponseUtils.makeResponse(exception));
    }

    /**
     * Error 레벨 로깅에 사용되는 메서드 입니다.
     *
     * @param status    발생한 예외에 대한 Http 상태 코드
     * @param exception 발생한 예외 객체
     * @author : 이성준
     * @since : 1.0
     */
    private static void loggingError(HttpStatus status, Exception exception) {
        log.error("[{}] {}", status, exception.getMessage());
    }

}
