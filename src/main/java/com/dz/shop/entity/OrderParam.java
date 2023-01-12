package com.dz.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderParam {
    private String checkNoList;
    private String userId;
    private String address;
    private String tel1;
    private String tel2;
    private String tel3;
    private String receiver;
    private String sheet;
    private List<String> stringList;
}
