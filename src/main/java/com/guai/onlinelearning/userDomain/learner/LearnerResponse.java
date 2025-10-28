package com.guai.onlinelearning.userDomain.learner;

import com.guai.onlinelearning.userDomain.Gender;
import com.guai.onlinelearning.userDomain.user.UserResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LearnerResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String phoneNumber;
    private Gender gender;
    private UserResponse user;
}
