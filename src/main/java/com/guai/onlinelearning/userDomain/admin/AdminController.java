package com.guai.onlinelearning.userDomain.admin;

import com.guai.onlinelearning.common.OperationResponse;
import com.guai.onlinelearning.exception.ErrorResponse;
import com.guai.onlinelearning.exception.StatusCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Managemen", description = "APIs for view and update Admin Info ")
public class AdminController {

    private final IAdminService adminService;

    @PutMapping("/{id}")
    @Operation(summary = "Update Admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update admin success",
                    content = @Content(schema = @Schema(implementation = OperationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Update admin failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "405", description = "Invalid method input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Admin already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "417", description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OperationResponse> updateAdmin(@PathVariable Integer id, @RequestBody @Valid AdminRequest request) {
        return ResponseEntity
                .status(200)
                .body(
                        OperationResponse.buildSuccessResponse(
                                StatusCode.SUCCESS,
                                "Update admin success",
                                adminService.update(id, request),
                                null)
                );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Admin By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved admin success",
                    content = @Content(schema = @Schema(implementation = OperationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Retrieved admin failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "405", description = "Invalid method input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "417", description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OperationResponse> getAdminById(@PathVariable Integer id) {
        return ResponseEntity
                .status(200)
                .body(
                        OperationResponse.buildSuccessResponse(
                                StatusCode.SUCCESS,
                                "Retrieved admin success",
                                adminService.findById(id),
                                null)
                );
    }
}
