package com.dz.shop.Dao;

import com.dz.shop.entity.OrderVO;

import java.util.List;

public interface OrderDAO {
    long add(OrderVO orderVO);
    long maxNum();
    List<OrderVO> mylist(String userId);
}
