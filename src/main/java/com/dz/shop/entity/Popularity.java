package com.dz.shop.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Popularity {
    private String bno;
    private String userId;
    private String type;
    private LocalDateTime register_date;
    private boolean isDelete;
}
