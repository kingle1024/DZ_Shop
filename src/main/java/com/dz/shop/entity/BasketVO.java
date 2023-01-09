package com.dz.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketVO {
    private String no;
    private String product_no;
    private int cnt;
    LocalDateTime register_date;
    LocalDateTime update_date;
}
