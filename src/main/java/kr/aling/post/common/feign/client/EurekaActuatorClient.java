package kr.aling.post.common.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * sample 용 open feign 인터페이스 입니다. eureka 서버의 actuator 정보를 확인할 수 있습니다.
 *
 * @author : 이성준
 * @since 1.0
 */
@FeignClient("aling-discovery-service")
public interface EurekaActuatorClient {

    @GetMapping("/actuator/health")
    ResponseEntity<String> health();

    @GetMapping("/actuator")
    ResponseEntity<String> actuator();
}
