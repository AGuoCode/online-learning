package com.guai.onlinelearning.userDomain.user;

import com.guai.onlinelearning.userDomain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toUser(String email, UserRole role) {
        String password = "";
        if (UserRole.ADMIN.equals(role)) {
            password = passwordEncoder.encode("123456");
        }
        return User.builder()
                .username(email)
                .password(password)
                .role(role)
                .enabled(false) // disable at first and enable after activation
                .locked(false)
                .build();
    }

    public UserResponse toUserResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
