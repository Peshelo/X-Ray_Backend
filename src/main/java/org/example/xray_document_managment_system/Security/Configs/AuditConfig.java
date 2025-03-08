package org.example.xray_document_managment_system.Security.Configs;

import lombok.RequiredArgsConstructor;
import org.example.xray_document_managment_system.Security.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@RequiredArgsConstructor
public class AuditConfig {

    private final UserService userService;

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return new AuditorAwareImpl(userService);
    }

}
