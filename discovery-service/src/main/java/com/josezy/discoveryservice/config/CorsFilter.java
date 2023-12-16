package com.josezy.discoveryservice.config;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class CorsFilter {

    public Predicate<ServerHttpRequest> isCorsRequired = request -> {
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return true;
        }
        ServerHttpRequest.Builder builder = request.mutate();
        builder.headers(httpHeaders -> {
            httpHeaders.add("Access-Control-Allow-Origin", "http://localhost:4200"); // Reemplaza con tu origen permitido
            httpHeaders.add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
            httpHeaders.add("Access-Control-Allow-Headers", "*");
            httpHeaders.add("Access-Control-Allow-Credentials", "true");
            httpHeaders.add("Access-Control-Max-Age", "3600");
        });
        return true;
    };
}
