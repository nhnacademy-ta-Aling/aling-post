package kr.aling.post.common.feign.sample;

import kr.aling.post.common.feign.client.EurekaActuatorClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * sample 용 컨트롤러입니다. eureka 서버의 actuator 정보를 확인할 수 있습니다.
 *
 * @author : 이성준
 * @since 1.0
 */

@RestController
@RequestMapping("/api/v1/eureka-openfeign")
@RequiredArgsConstructor
public class SampleOpenFeignClientController {

    private final EurekaActuatorClient eurekaActuatorClient;

    @GetMapping("/actuator")
    public ResponseEntity<String> openfeignSample() {

        return eurekaActuatorClient.actuator();

    }
}
