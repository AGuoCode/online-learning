package com.guai.onlinelearning.common;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponse<T> {

    private int page;
    private int pageSize;
    private int totalPages;
    private long total;
    private List<T> content;
    private boolean isFirst;
    private boolean isLast;
    private boolean isEmpty;

}
