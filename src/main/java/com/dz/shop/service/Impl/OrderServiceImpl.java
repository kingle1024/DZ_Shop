package com.dz.shop.service.Impl;

import com.dz.shop.Dao.BasketDAO;
import com.dz.shop.Dao.OrderDAO;
import com.dz.shop.entity.OrderEnum;
import com.dz.shop.entity.OrderParam;
import com.dz.shop.entity.OrderVO;
import com.dz.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    BasketDAO basketDAO;
    @Autowired
    OrderDAO orderDAO;

    @Override
    public List<Map<String, Object>> list(OrderParam orderParam) {
        System.out.println(" > " + orderParam.getCheckList());
        System.out.println("Arrays.asList(orderParam.getCheckList()) = " + Collections.singletonList(orderParam.getCheckList()));

        Map<String, Object> param = new HashMap<>();
        param.put("ids", orderParam.getCheckList());
        param.put("userId", orderParam.getUserId());
        // 리스트를 만듬
        return basketDAO.preOrderList(param);
    }

    @Transactional
    @Override
    public long add(List<Map<String, Object>> list) {
        System.out.println("list = " + list);
        long maxNum = orderDAO.maxNum();
        maxNum +=1;
        System.out.println(maxNum);
        for (Map<String, Object> m : list) {
            System.out.println("m = " + m);
            OrderVO orderVO = OrderVO.builder()
                    .title((String) m.get("title"))
                    .userId((String) m.get("userId"))
                    .price((Integer) m.get("price"))
                    .product_no((Integer) m.get("no"))
                    .cnt((Integer) m.get("cnt"))
                    .status(OrderEnum.READY.name())
                    .order_number(maxNum)
                    .build();
            if (orderDAO.add(orderVO) < 0) throw new RuntimeException();
            maxNum += 1;
        }

        return 0;
    }

    @Override
    public List<OrderVO> mylist(String userId) {
        return orderDAO.mylist(userId);
    }
}
