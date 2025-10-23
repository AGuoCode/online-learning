package com.guai.onlinelearning.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationErrorResponse {

    private String field;
    private String message;
}
