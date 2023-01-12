package com.dz.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderVO {
    private String no;
    private String title;
    private int price;
    private int cnt;
    private String userId;
    private String receiver;
    private String status;
    private String tel1;
    private String tel2;
    private String tel3;
    private int product_no;
    private long order_number;
    private String address;
    private LocalDateTime register_date;
}
