package com.guai.onlinelearning.userDomain.position;

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
@RequestMapping("/positions")
@Tag(name = "Positions", description = "APIs for Position Management : CRUD positions and view positions by pagination")
@RequiredArgsConstructor
public class PositionController {

    private final IPositionService positionService;

    @PostMapping
    @Operation(summary = "Create Position")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Sample Create Position",
            content = @Content(
                    examples = @ExampleObject(name = "Example Create Position Request",
                            value = """
                                       {
                                           "name": "Your Position"
                                        }
                                    """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create position success",
                    content = @Content(schema = @Schema(implementation = OperationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Create position failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "405", description = "Invalid method input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Position already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "417", description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OperationResponse> savePosition(@RequestBody @Valid PositionRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OperationResponse.buildSuccessResponse(
                                StatusCode.CREATED,
                                "Created position success",
                                positionService.create(request),
                                null,
                                null
                        )
                );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Position")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Sample Update Position",
            content = @Content(
                    examples = @ExampleObject(name = "Example Update Position Request",
                            value = """
                                       {
                                           "name": "Your Position"
                                        }
                                    """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update position success",
                    content = @Content(schema = @Schema(implementation = OperationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Update position failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "405", description = "Invalid method input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Position already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "417", description = "Validation failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OperationResponse> updatePosition(
            @PathVariable Integer id,
            @RequestBody @Valid PositionRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(OperationResponse.buildSuccessResponse(
                                StatusCode.SUCCESS,
                                "Updated position success",
                                positionService.update(id, request),
                                null,
                                null
                        )
                );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Position By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved position success",
                    content = @Content(schema = @Schema(implementation = OperationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Retrieved position failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "405", description = "Invalid method input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OperationResponse> findOnePositionById(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(OperationResponse.buildSuccessResponse(
                                StatusCode.SUCCESS,
                                "Retrieved position success",
                                null,
                                positionService.findById(id),
                                null
                        )
                );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Position By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete position success",
                    content = @Content(schema = @Schema(implementation = OperationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Delete position failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "405", description = "Invalid method input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OperationResponse> deletePositionById(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(OperationResponse.buildSuccessResponse(
                                StatusCode.SUCCESS,
                                "Delete position success",
                                null,
                                positionService.findById(id),
                                null
                        )
                );
    }

    @GetMapping
    @Operation(summary = "Get Position By Pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all positions by pagination success",
                    content = @Content(schema = @Schema(implementation = OperationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Retrieved all positions by pagination failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "405", description = "Invalid method input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<OperationResponse> findPositionByPagination(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(OperationResponse.buildSuccessResponse(
                                StatusCode.SUCCESS,
                                "Retrieved all positions by pagination success",
                                null,
                                positionService.findAll(
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
