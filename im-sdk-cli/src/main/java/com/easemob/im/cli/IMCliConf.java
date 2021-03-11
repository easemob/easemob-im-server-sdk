package com.easemob.im.cli;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IMCliConf {

    @Bean
    @ConfigurationProperties(prefix = "easemob.im")
    public EMProperties.Builder propertiesBuilder() {
        return EMProperties.builder();
    }

    @Bean
    public EMService service(EMProperties.Builder propertiesBuilder) {
        EMService service = new EMService(propertiesBuilder.build());
        return service;
    }
}
