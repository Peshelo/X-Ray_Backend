package org.example.xray_document_managment_system.Security.Configs;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(
                "users",
                "roles",
                "permissions",
                "privileges",
                "roles-permissions",
                "users-roles",
                "users-permissions",
                "users-privileges",
                "users-roles-permissions",
                "users-roles-privileges",
                "users-permissions-privileges",
                "users-roles-permissions-privileges"
        );
    }
}

