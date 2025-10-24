package com.guai.onlinelearning.auth;

import com.guai.onlinelearning.common.OperationResponse;
import com.guai.onlinelearning.exception.ErrorResponse;
import com.guai.onlinelearning.exception.StatusCode;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth Management", description = "APIs for user authentication, registration, logout and token management")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Sample Update Admin Information",
            content = @Content(
                    examples = @ExampleObject(name = "Example Update Admin Request",
                            value = """
                                       {
                                         "username": "johndoe@gmai.com",
                                         "password": "Your password"
                                       }
                                    """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login success",
                    content = @Content(schema = @Schema(implementation = OperationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Login failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "405", description = "Invalid method input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "417", description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OperationResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        return ResponseEntity.status(200).body(
                OperationResponse.buildSuccessResponse(
                        StatusCode.SUCCESS,
                        "Login success",
                        null,
                        authService.login(authRequest),
                        null)
        );

    }
}
