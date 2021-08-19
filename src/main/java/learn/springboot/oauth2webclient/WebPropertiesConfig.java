package learn.springboot.oauth2webclient;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api.gateway")
@Data
public class WebPropertiesConfig {

    private String url;

    private String couponUrl;

    private boolean connectToCouponResource;

}
