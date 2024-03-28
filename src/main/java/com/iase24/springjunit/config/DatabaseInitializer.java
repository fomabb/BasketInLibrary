package com.iase24.springjunit.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DatabaseInitializer {

    private final DataSource dataSource;

    @PostConstruct
    public void init() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("init_database.sql"));
        populator.execute(dataSource);
    }
}
