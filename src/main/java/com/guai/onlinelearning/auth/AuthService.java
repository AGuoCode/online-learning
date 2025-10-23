package com.guai.onlinelearning.auth;

import com.guai.onlinelearning.config.security.JwtService;
import com.guai.onlinelearning.userDomain.user.User;
import com.guai.onlinelearning.userDomain.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse login(@Valid AuthRequest authRequest) {
        User user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Username not found")
        );
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Wrong Password");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getUsername());
        claims.put("role", user.getRole());
        String token = jwtService.generatetoken(user, claims);
        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().toString())
                .build();
    }
}
