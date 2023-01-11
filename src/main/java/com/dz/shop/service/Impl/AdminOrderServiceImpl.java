package com.dz.shop.service.Impl;

import com.dz.shop.entity.OrderVO;
import com.dz.shop.Dao.AdminOrderDAO;
import com.dz.shop.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    @Autowired
    AdminOrderDAO adminOrderDAO;

    @Override
    public List<OrderVO> list() {
        return adminOrderDAO.list();
    }
}
