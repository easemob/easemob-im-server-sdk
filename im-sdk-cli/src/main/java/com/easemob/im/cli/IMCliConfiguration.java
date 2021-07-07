package com.easemob.im.cli;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource(value = "file:${user.home}/.easemob/config.properties")
})
@EnableConfigurationProperties({IMCliProperties.class})
public class IMCliConfiguration {

    @Bean
    public EMService service(IMCliProperties cliProperties) {
        EMProperties properties = EMProperties.builder()
                .setBaseUri(cliProperties.getBaseUri())
                .setAppkey(cliProperties.getAppkey())
                .setClientId(cliProperties.getClientId())
                .setClientSecret(cliProperties.getClientSecret())
                .build();

        EMService service = new EMService(properties);

        return service;
    }
}
