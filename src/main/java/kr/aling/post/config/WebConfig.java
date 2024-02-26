package kr.aling.post.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RestAPI 와 관련된 Web 설정 Bean 을 관리하는 Java Config 입니다.
 *
 * @author : 이성준
 * @since 1.0
 */
@Configuration
public class WebConfig {

    /**
     * ObjectMapper Bean.
     *
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }

}
