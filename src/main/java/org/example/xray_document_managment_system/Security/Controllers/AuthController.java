package org.example.xray_document_managment_system.Security.Controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.xray_document_managment_system.Security.Models.LoginResponse;
import org.example.xray_document_managment_system.Security.Models.Users;
import org.example.xray_document_managment_system.Security.Service.TokenService;
import org.example.xray_document_managment_system.Security.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication and Authorization Managment", description = "Manage security")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.saveUser(user);
    }


    @PostMapping("/login")
    public LoginResponse token(@RequestBody LoginRequest loginRequest) {
        try {
            var user = userService.findUserByEmail(loginRequest.email());

            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            if (userService.isAccountLocked(user.getEmail())) {
                throw new LockedException("Account is locked. Please try again later.");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );

            userService.resetFailedLoginAttempts(user.getEmail());


            return LoginResponse.builder()
                    .token(tokenService.generateToken(authentication))
                    .role(user.getRoles())
                    .message("Login successful")
                    .success(true)
                    .build();

//            return "token : " + tokenService.generateToken(authentication) + "\n role : " + authentication.getAuthorities();

        } catch (UsernameNotFoundException e) {
            userService.incrementFailedLoginAttempts(loginRequest.email());

            log.warn("Failed login attempt for user: {}", loginRequest.email());

            return LoginResponse.builder()
                    .message("Invalid credentials")
                    .success(false)
                    .build();

        } catch (LockedException e) {
            return LoginResponse.builder()
                    .message(e.getMessage())
                    .success(false)
                    .build();
        } catch (AuthenticationException e) {
            userService.incrementFailedLoginAttempts(loginRequest.email());

            log.warn("Failed login attempt for user: {}", loginRequest.email());

            return LoginResponse.builder()
                    .message("Invalid credentials")
                    .success(false)
                    .build();
        }

    }

}
