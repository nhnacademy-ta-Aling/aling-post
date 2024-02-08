package kr.aling.post.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service, Transactional(readOnly = true) 어노테이션이 반복을 줄이고
 * Transactional 에 대한 속성 누락을 방지 효과를 기대할 수 있을 거 같습니다.
 *
 * @author : 이성준
 */
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(readOnly = true)
@Service
public @interface ReadService {
}
