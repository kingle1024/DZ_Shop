package com.dz.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {
    private String no;
    private String title;
    private String content;
    private String writer;
    private String thumbnail;
    private int price;
    private int like_count;
    private int dislike_count;
    private LocalDateTime regist_date;
    private LocalDateTime update_date;
    private boolean delete_yn;
}
