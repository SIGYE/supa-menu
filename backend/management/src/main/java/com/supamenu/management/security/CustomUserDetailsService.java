package com.supamenu.management.security;

import com.supamenu.management.models.User;
import com.supamenu.management.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Transactional
    public UserDetails loadByUserId(UUID id){
        User user = this.userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found with id" +id));
        return UserPrincipal.create(user);
    }
    @Transactional
    public UserDetails loadUserByUsername(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found by email" +email));
        return UserPrincipal.create(user);
    }
}
