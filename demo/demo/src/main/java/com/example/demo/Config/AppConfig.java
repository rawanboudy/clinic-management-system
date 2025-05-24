package com.example.demo.Config;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.ComponentScan;

@Configuration
@ComponentScan(basePackages = "com.example.demo")
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/clinic?useSSL=true")
                .username("root")
                .password("") // Ensure this is the correct password
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
 
}
