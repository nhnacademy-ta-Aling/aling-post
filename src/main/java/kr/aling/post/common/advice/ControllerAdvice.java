package kr.aling.post.common.advice;

import kr.aling.post.normalpost.exception.NormalPostNotFoundException;
import kr.aling.post.post.exception.PostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Rest Api 전역 예외처리
 *
 * @author : 이성준
 * @since : 1.0
 */

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({PostNotFoundException.class, NormalPostNotFoundException.class})
    public ResponseEntity<String> handleNotFoundExceptions(RuntimeException e){
        log.error("[{}] {}", HttpStatus.NOT_FOUND, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

}
