package kr.aling.post.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
@Getter
@Setter
@ConfigurationProperties(prefix = "aling.server")
public class AlingUrlProperties {

    private String gatewayUrl;
    private String fileGatewayUrl;
}
