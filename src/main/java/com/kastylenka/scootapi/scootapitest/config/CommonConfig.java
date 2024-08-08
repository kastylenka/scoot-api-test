package com.kastylenka.scootapi.scootapitest.config;

import org.geotools.referencing.GeodeticCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class CommonConfig {

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public GeodeticCalculator geodeticCalculator() {
        return new GeodeticCalculator();
    }
}
