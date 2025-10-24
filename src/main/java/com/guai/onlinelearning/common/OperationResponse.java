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

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"statusCode", "status", "message", "timestamp", "data", "resList"})
public class OperationResponse extends BaseResponse {
    private Integer id;
    private Object data;
    private List<Object> dataList;

    public static OperationResponse buildSuccessResponse(StatusCode statusCode, String successMessage, Integer id, Object data, List<Object> dataList) {
        return OperationResponse.builder()
                .statusCode(statusCode.getCode())
                .status(statusCode.getMessage())
                .message(successMessage)
                .timestamp(LocalDateTime.now())
                .id(id)
                .data(data)
                .dataList(dataList)
                .build();
    }
}
