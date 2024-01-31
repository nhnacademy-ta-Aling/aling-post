package kr.aling.post.base;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 생성 시간 JPA Auditing 을 위한 클래스.
 *
 * @author 정유진
 * @since 1.0
 **/
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseCreateTimeEntity {
    @CreatedDate
    @Column(name = "create_at", updatable = false)
    private LocalDateTime createAt;
}
