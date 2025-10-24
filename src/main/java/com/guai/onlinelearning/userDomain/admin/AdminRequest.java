package com.guai.onlinelearning.userDomain.admin;

import com.guai.onlinelearning.userDomain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRequest {

    @NotBlank(message = "Name cannot be empty.")
    private String name;
    @NotBlank(message = "Email cannot be empty.")
    @Email(message = "Invalid Email Format")
    private String email;
    @NotBlank(message = "Article cannot be empty.")
    private String article;
    @NotBlank(message = "Professional cannot be empty.")
    private String professional;
    @NotBlank(message = "Bio cannot be empty.")
    private String bio;
    private List<String> certificates;
    @NotNull(message = "Name cannot be empty.")
    private UserRole role;
}
