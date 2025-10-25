package com.guai.onlinelearning.userDomain.staff;

import com.guai.onlinelearning.userDomain.Gender;
import com.guai.onlinelearning.userDomain.position.PositionResponse;
import com.guai.onlinelearning.userDomain.user.UserResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffResponse {

    private Integer id;
    private String fullName;
    private String email;
    private String nrc;
    private String dateOfBirth;
    private String phoneNumber;
    private String address;
    private Gender gender;
    private PositionResponse position;
    private UserResponse user;
}
