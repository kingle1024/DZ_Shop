package com.dz.shop.service;

import com.dz.shop.entity.AdminOrderParam;
import com.dz.shop.entity.OrderVO;

import java.util.List;

public interface AdminOrderService {
    List<OrderVO> list();

    long updateDeliveryStatus(AdminOrderParam adminOrderParam);

    OrderVO detail(String no);
}
