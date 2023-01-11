package com.dz.shop.Dao;

import com.dz.shop.entity.OrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminOrderDAO {
    List<OrderVO> list();
}
