package com.guai.onlinelearning.userDomain.position;

import org.springframework.stereotype.Service;

@Service
public class PositionMapper {


    public Position toPosition(PositionRequest positionRequest) {
        return Position.builder()
                .name(positionRequest.getName())
                .build();
    }

    public Position mergeForUpdatePosition(Integer id, PositionRequest positionRequest) {
        return Position.builder()
                .id(id)
                .name(positionRequest.getName())
                .build();
    }

    public PositionResponse toPositionResponse(Position position) {
        return PositionResponse.builder()
                .id(position.getId())
                .name(position.getName())
                .build();
    }
}
