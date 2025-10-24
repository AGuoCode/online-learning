package com.guai.onlinelearning.userDomain.position;

import com.guai.onlinelearning.base.IBaseService;

import java.util.Optional;

public interface IPositionService extends IBaseService<PositionRequest, PositionResponse> {
    Optional<PositionResponse> getPositionByName(String name);
}
