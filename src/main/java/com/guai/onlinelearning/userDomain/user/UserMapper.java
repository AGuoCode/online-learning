package com.guai.onlinelearning.userDomain.user;

import com.guai.onlinelearning.userDomain.UserRole;
import com.guai.onlinelearning.utilities.GenerateOTP;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toUser(String email, UserRole role) {
        return User.builder()
                .username(email)
                .password(passwordEncoder.encode(GenerateOTP.generateOTPCode(6)))
                .role(role)
                .enabled(true) // TODO currently enable user and enable by email verify later
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
