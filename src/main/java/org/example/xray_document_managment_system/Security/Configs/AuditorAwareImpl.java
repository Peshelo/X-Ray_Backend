package org.example.xray_document_managment_system.Security.Configs;

import lombok.RequiredArgsConstructor;
import org.example.xray_document_managment_system.Security.Models.Users;
import org.example.xray_document_managment_system.Security.Service.UserService;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<Long> {

    private final UserService userService;



    public Optional<Long> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .flatMap(principal -> {
                    if (principal instanceof Jwt) {
                        Jwt jwt = (Jwt) principal;
                        String email = jwt.getSubject();
                        Users user = userService.findUserByEmail(email);
                        return Optional.ofNullable(user).map(Users::getId);

                    } else if (principal instanceof String) {


                        String email = (String) principal;
                        Users user = userService.findUserByEmail(email);
                        return Optional.ofNullable(user).map(Users::getId);
                    } else {
                        return Optional.empty();
                    }
                });
        }

}

