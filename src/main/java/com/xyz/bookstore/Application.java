package com.xyz.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableJpaRepositories
@EnableGlobalMethodSecurity(securedEnabled = true)
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

}
