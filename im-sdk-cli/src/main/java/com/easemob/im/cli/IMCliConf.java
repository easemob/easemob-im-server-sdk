package com.easemob.im.cli;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource(value = "file:${user.home}/.easemob/config.properties", ignoreResourceNotFound = true)
})
public class IMCliConf {

    @Bean
    @ConfigurationProperties(prefix = "im")
    public EMProperties.Builder propertiesBuilder() {
        return EMProperties.builder();
    }

    @Bean
    public EMService service(EMProperties.Builder propertiesBuilder) {
        EMService service = new EMService(propertiesBuilder.build());
        return service;
    }

}
