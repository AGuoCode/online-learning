package com.guai.onlinelearning.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StatusCode {

    SUCCESS(200, "Success"),
    CREATED(201, "Create Suceess"),
    BAD_REQUEST(400, "BAD REQUEST"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    FORBIDDEN(403, "FORBIDDEN"),
    NOT_FOUND(404, "NOT FOUND"),
    CONFLICT(409, "CONFLICT"),
    VALIDATION_ERROR(417, "VALIDATION ERROR"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR");

    private int code;
    private String message;
}
