package com.dp.productservice.model;

import java.util.List;

public record OrderProductInfoRequest(
        List<Long> ids
) {
}
