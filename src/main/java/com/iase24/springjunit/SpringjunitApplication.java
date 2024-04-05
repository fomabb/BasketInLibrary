package com.iase24.springjunit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringjunitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringjunitApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .maxAge(3600L)
                        .allowedHeaders("Content-Type, Accept, X-Requested-With, x-customer-header-1, x-customer-header-2")
                        .exposedHeaders("X-Get-Header");
            }
        };
    }
}
