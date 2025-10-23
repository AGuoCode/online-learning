package com.guai.onlinelearning.userDomain.user;


import com.guai.onlinelearning.userDomain.UserRole;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private int id;
    private String username;
    private UserRole role;
}
