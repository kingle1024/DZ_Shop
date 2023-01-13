package com.dz.shop.Dao;

import com.dz.shop.entity.AdminOrderParam;
import com.dz.shop.entity.OrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminOrderDAO {
    List<OrderVO> list();

    long updateDeliveryStatus(AdminOrderParam adminOrderParam);

    OrderVO detail(String no);
}
