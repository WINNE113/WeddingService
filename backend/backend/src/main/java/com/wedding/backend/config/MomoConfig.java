package com.wedding.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
@ConfigurationProperties(prefix = "momo")
@Component
@Data
public class MomoConfig {
    private String configName = "Momo";
    private String partnerCode;
    private String returnUrl;
    private String ipnUrl;
    private String accessKey;
    private String secretKey;
    private String paymentUrl;
}
