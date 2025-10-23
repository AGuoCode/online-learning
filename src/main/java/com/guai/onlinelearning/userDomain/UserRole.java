package com.guai.onlinelearning.userDomain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    STAFF("ROLE_STAFF"),
    LEARNER("ROLE_LEARNER");

    private String name;
}
