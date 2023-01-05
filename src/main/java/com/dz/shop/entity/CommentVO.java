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
public class CommentVO {
    private String no;
    private String product_no;
    private String parent_no;
    private String comment;
    private String writer;
    private String writer_id;
    public LocalDateTime register_date;
    private LocalDateTime update_date;
    private boolean delete_yn;
}
