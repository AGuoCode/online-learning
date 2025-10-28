package com.guai.onlinelearning.userDomain.learner;

import com.guai.onlinelearning.exception.BusinessException;
import com.guai.onlinelearning.userDomain.Gender;
import com.guai.onlinelearning.userDomain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LearnerRequest {

    @NotBlank(message = "First Name cannot be empty.")
    private String firstName;
    @NotBlank(message = "Last Name cannot be empty.")
    private String lastName;
    @NotBlank(message = "Email cannot be empty.")
    @Email(message = "Invalid Email Format")
    private String email;
    private String dateOfBirth;
    private String phoneNumber;
    private Gender gender;
    @NotNull(message = "User Role is required")
    private UserRole role;

    public void isValid() {
        if (!this.role.equals(UserRole.LEARNER)) {
            throw new BusinessException("Invalid Role");
        }
    }
}
