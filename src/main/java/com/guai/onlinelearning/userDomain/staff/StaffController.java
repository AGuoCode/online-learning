package com.guai.onlinelearning.userDomain.staff;

import com.guai.onlinelearning.common.OperationResponse;
import com.guai.onlinelearning.exception.ErrorResponse;
import com.guai.onlinelearning.exception.StatusCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staffs")
@Tag(name = "Staffs", description = "APIs for Staff Management")
@RequiredArgsConstructor
public class StaffController {

    private final IStaffService staffService;

    @PostMapping
    @Operation(summary = "Create Staff")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Sample Create Staff",
            content = @Content(
                    examples = @ExampleObject(name = "Example Create Staff Request",
                            value = """
                                       {
                                           "firstName": "John",
                                           "lastName": "Doe",
                                           "email": "johndoe@gmail.com",
                                           "nrc": "Your Staff Nationality Card Number",
                                           "dateOfBirth": "yyyyMMdd",
                                           "phoneNumber": "092748273482",
                                           "address": "Your Address",
                                           "gender": "MALE",
                                           "positionId": 0,
                                           "role": "STAFF"
                                         }
                                    """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create staff success",
                    content = @Content(schema = @Schema(implementation = OperationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Create staff failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "405", description = "Invalid method input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Staff already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "417", description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OperationResponse> saveStaff(@RequestBody @Valid StaffRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OperationResponse.buildSuccessResponse(
                                StatusCode.CREATED,
                                "Created staff success",
                                staffService.create(request),
                                null,
                                null
                        )
                );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Staff")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Sample Create Staff",
            content = @Content(
                    examples = @ExampleObject(name = "Example Create Staff Request",
                            value = """
                                       {
                                           "firstName": "John",
                                           "lastName": "Doe",
                                           "email": "johndoe@gmail.com",
                                           "nrc": "Your Staff Nationality Card Number",
                                           "dateOfBirth": "yyyyMMdd",
                                           "phoneNumber": "092748273482",
                                           "address": "Your Address",
                                           "gender": "MALE",
                                           "positionId": 0,
                                           "role": "STAFF"
                                         }
                                    """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update staff success",
                    content = @Content(schema = @Schema(implementation = OperationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Update staff failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "405", description = "Invalid method input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Staff already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "417", description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OperationResponse> updateStaff(
            @PathVariable Integer id,
            @RequestBody @Valid StaffRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(OperationResponse.buildSuccessResponse(
                                StatusCode.SUCCESS,
                                "Updated staff success",
                                staffService.update(id, request),
                                null,
                                null
                        )
                );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Staff By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved staff success",
                    content = @Content(schema = @Schema(implementation = OperationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Retrieved staff failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "405", description = "Invalid method input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OperationResponse> findOneStaffById(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(OperationResponse.buildSuccessResponse(
                                StatusCode.SUCCESS,
                                "Retrieved staff success",
                                null,
                                staffService.findById(id),
                                null
                        )
                );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Staff By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete staff success",
                    content = @Content(schema = @Schema(implementation = OperationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Delete staff failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "405", description = "Invalid method input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OperationResponse> deleteStaffById(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(OperationResponse.buildSuccessResponse(
                                StatusCode.SUCCESS,
                                "Delete staff success",
                                null,
                                staffService.findById(id),
                                null
                        )
                );
    }

    @GetMapping
    @Operation(summary = "Get Staff By Pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all staffs by pagination success",
                    content = @Content(schema = @Schema(implementation = OperationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Retrieved all staffs by pagination failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "405", description = "Invalid method input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OperationResponse> findStaffByPagination(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(OperationResponse.buildSuccessResponse(
                                StatusCode.SUCCESS,
                                "Retrieved all staffs by pagination success",
                                null,
                                staffService.findAll(
                                        PageRequest.of(
                                                page,
                                                size,
                                                Sort.by("CreatedDate"
                                                ).descending()
                                        )
                                ),
                                null
                        )
                );
    }

}
