package com.guai.onlinelearning.auth;

import com.guai.onlinelearning.common.OperationResponse;
import com.guai.onlinelearning.exception.StatusCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<OperationResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        return ResponseEntity.status(200).body(
                OperationResponse.buildSuccessResponse(
                        StatusCode.SUCCESS,
                        "Login Successful",
                        authService.login(authRequest),
                        null)
        );

    }
}
