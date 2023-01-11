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
    private String checkList;
    private String userId;
    private String sheet;
    private List<String> stringList;
}
