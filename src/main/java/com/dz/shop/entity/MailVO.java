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
public class MailVO {
    private String value;
    private String retValue;
    private String email;
    private String user_id;
    private String content;
    private LocalDateTime create_date;
}
