package com.blogger.blogger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;



@Component
@Data
@ConfigurationProperties(prefix = "com.blogger")
public class MyConfig {

    private String fileServiceUrl;

}
