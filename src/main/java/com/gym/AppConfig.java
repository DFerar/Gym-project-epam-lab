package com.gym;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@ComponentScan
@PropertySource("classpath:application.yml")
@EnableJpaRepositories
public class AppConfig {

}
