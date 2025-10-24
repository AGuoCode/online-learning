package com.guai.onlinelearning.userDomain.admin;

import com.guai.onlinelearning.userDomain.user.UserResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminResponse {

    private Integer id;
    private String name;
    private String email;
    private String article;
    private String professional;
    private String bio;
    private List<String> certificates;
    private UserResponse user;

}
