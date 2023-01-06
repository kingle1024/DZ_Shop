package com.dz.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardFile {
    private int f_id;
    private int number;
    private String org_name;
    private String real_name;
    private String content_type;
    private int length;
    private String thumbnail;
    private LocalDateTime register_date;
}
