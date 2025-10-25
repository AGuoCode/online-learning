package com.guai.onlinelearning.userDomain.staff;

import com.guai.onlinelearning.exception.BusinessException;
import com.guai.onlinelearning.userDomain.Gender;
import com.guai.onlinelearning.userDomain.UserRole;
import com.guai.onlinelearning.utilities.DateFormatter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffRequest {

    @NotBlank(message = "First Name is required")
    private String firstName;
    @NotBlank(message = "Last Name is required")
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Format")
    private String email;
    private String nrc;
    private String dateOfBirth;
    private String phoneNumber;
    private String address;
    private Gender gender;
    @NotNull(message = "Position is required")
    private Integer positionId;
    @NotNull(message = "User Role is required")
    private UserRole role;

    public void isValid() {
        if (!this.role.equals(UserRole.STAFF)) {
            throw new BusinessException("Invalid Role");
        }
        DateFormatter.checkDOB_Format(this.dateOfBirth);
    }
}
