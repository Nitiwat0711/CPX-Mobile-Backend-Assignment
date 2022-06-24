package com.cpx.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;

@Configuration
public class LogbookConfiguration {
    @Bean
    public Logbook logbook() {
        Logbook logbook = Logbook.create();
        return logbook;
    }
}
