package com.guai.onlinelearning.auth;

import com.guai.onlinelearning.base.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AuthResponse extends BaseResponse {

    private String token;
    private String username;
    private String role;
}
