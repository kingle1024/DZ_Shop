package com.dz.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketParam {
    private String no;
    private String userId;
    private String product_no;
    private String type;
    private int cnt;
}
