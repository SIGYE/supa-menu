package com.supamenu.management.serviceImpl;

import com.supamenu.management.models.User;
import com.supamenu.management.payload.response.JwtAuthenticationResponse;
import com.supamenu.management.security.JwtTokenProvider;
import com.supamenu.management.service.AuthService;
import com.supamenu.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public JwtAuthenticationResponse login(String email, String password){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = null;
        try{
            jwt = jwtTokenProvider.generateToken(authentication);
        }catch (Exception e){
            throw new IllegalStateException("Error generating token", e);
        }
        User user = this.userService.getByEmail(email);
        return new JwtAuthenticationResponse(jwt,user);
    }
}
