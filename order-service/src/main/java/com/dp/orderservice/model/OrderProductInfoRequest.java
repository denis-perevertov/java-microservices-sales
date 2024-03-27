package com.dp.orderservice.model;

import java.util.List;

public record OrderProductInfoRequest(
        List<Long> ids
) {
}
