package com.guai.onlinelearning.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.guai.onlinelearning.base.BaseResponse;
import com.guai.onlinelearning.exception.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"statusCode", "status", "message", "timestamp", "data", "resList"})
public class ApiResponse extends BaseResponse {
    private Object data;
    private List<Object> dataList;

    public static ApiResponse buildSuccessResponse(StatusCode statusCode, String successMessage, Object data, List<Object> dataList) {
        return ApiResponse.builder()
                .statusCode(statusCode.getCode())
                .status(statusCode.getMessage())
                .message(successMessage)
                .data(data)
                .dataList(dataList)
                .build();
    }
}
