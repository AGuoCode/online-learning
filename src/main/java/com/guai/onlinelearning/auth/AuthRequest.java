package com.guai.onlinelearning.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {

    @NotBlank(message = "Username cannot be empty.")
    @Email(message = "Invalid Email Format")
    private String username;
    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 6)
    private String password;
}
