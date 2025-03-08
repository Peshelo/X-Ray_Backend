package org.example.xray_document_managment_system.Security.Service;

import lombok.RequiredArgsConstructor;
import org.example.xray_document_managment_system.Security.Models.Users;
import org.example.xray_document_managment_system.Security.Repositories.UserRepository;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ResourceLoader resourceLoader;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public ResponseEntity<String> saveUser(Users user) {
        if(userRepository.existsByEmail(user.getUsername())){
            //return 409 conflict
            return ResponseEntity.status(409).body("User with email or username already exists!");
        }
        user.setAccountLocked(false);
        user.setFailedLoginAttempts(0);

        //
        return ResponseEntity.ok("User saved!");
    }


    public Users findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);  // Now you can use the base Users class
    }


    public void incrementFailedLoginAttempts(String email) {
        Optional<Users> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            int failedLoginAttempts = user.getFailedLoginAttempts() + 1;
            user.setFailedLoginAttempts(failedLoginAttempts);
            if (failedLoginAttempts >= 20) {
                user.setAccountLocked(true);
            }
            userRepository.save(user);
        }
    }

    public void resetFailedLoginAttempts(String email) {
        Optional<Users> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            user.setFailedLoginAttempts(0);
            userRepository.save(user);
        }
    }

    public boolean isAccountLocked(String email) {
        Optional<Users> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().isAccountLocked();
    }
}
