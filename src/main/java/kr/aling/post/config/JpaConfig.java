package kr.aling.post.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 데이터 액세스 레이어인 JPA 관련 설정을 하는 Java Config 입니다. <Br> 엔티티 생성/수정 시간 값을 자동으로 변경하는 Spring Data Jpa Auditing 설정을 포함하고 있습니다.
 *
 * @author : 이성준
 * @since 1.0
 */
@EnableJpaAuditing
@Configuration
public class JpaConfig {

}
