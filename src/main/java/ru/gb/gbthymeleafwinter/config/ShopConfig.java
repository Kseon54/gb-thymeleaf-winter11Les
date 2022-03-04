package ru.gb.gbthymeleafwinter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareBean")
public class ShopConfig {

    @Bean
    public AuditorAware<String> auditorAwareBean() {
        return () -> Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
