package com.ascencio.dev.springwebfluxapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class CorsConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .cors().and()
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/**").permitAll()
                .anyExchange().authenticated();
        return http.build();
    }
}
