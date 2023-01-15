package com.dz.shop.Delivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryStatus {
    private int no;
    private String status;
    private String name;
}
