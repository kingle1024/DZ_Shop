package com.dz.shop.service;

import com.dz.shop.entity.OrderParam;
import com.dz.shop.entity.OrderVO;

import java.util.List;
import java.util.Map;

public interface OrderService {
    List<Map<String, Object>> list(OrderParam userId);
    long add(List<Map<String, Object>> list);

    List<OrderVO> mylist(String userId);
}
