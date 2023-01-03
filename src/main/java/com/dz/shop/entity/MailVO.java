package com.dz.shop.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class MailVO {
    private String value;
    private String email;
    private LocalDateTime create_date;

}
