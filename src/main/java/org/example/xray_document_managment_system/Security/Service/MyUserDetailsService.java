package org.example.xray_document_managment_system.Security.Service;

import lombok.RequiredArgsConstructor;
import org.example.xray_document_managment_system.Security.Models.SecurityUser;
import org.example.xray_document_managment_system.Security.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository
                .findByEmail(email)
                .map(SecurityUser::new)
                .orElseThrow(()-> new UsernameNotFoundException("Username " + email + " not found!"));
    }
}
