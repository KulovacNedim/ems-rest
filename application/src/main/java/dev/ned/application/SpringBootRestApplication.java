package dev.ned.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableWebMvc
@SpringBootApplication
@EntityScan("dev.ned")
@EnableJpaRepositories("dev.ned")
@ComponentScan(basePackages = {"dev.ned"})
public class SpringBootRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestApplication.class);
    }
}