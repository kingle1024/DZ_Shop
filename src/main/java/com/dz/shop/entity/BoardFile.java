package com.dz.shop.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class BoardFile {
    private int fid;
    private int number;
    private String org_name;
    private String real_name;
    private String content_type;
    private int length;
    private String thumbnail;
    private LocalDateTime register_date;
}
