package com.guai.onlinelearning.base;

import com.guai.onlinelearning.common.PaginationResponse;
import org.springframework.data.domain.Pageable;

public interface IBaseService<Request, Response> {

    Integer create(Request request);

    Integer update(Integer id, Request request);

    Response findById(Integer id);

    void delete(Integer id);

    boolean existsById(Integer id);

    PaginationResponse<Response> findAll(Pageable pageable);
}
