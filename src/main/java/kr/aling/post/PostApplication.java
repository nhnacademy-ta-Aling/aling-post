package kr.aling.post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * PostApplication.
 *
 * @author 정유진
 * @since 1.0
 **/
@EnableJpaAuditing
@SpringBootApplication
public class PostApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostApplication.class, args);
    }

}
