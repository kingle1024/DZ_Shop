package com.dz.shop.service.Impl;

import com.dz.shop.Dao.BasketDAO;
import com.dz.shop.Dao.OrderDAO;
import com.dz.shop.entity.BasketParam;
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
        // join product_basket and product
        System.out.println("OrderServiceImpl.list");
        System.out.println("orderParam = " + orderParam);
        Map<String, Object> param = new HashMap<>();

        param.put("checkNoList", orderParam.getCheckNoList());
        param.put("userId", orderParam.getUserId());
        // 리스트를 만듬
        return basketDAO.preOrderList(param);
    }

    @Transactional
    @Override
    public long add(List<Map<String, Object>> list, OrderParam orderParam) {
        System.out.println("OrderServiceImpl.add");
        long maxNum = orderDAO.maxNum();
        System.out.println("orderParam = " + orderParam);
        maxNum +=1;
        for (Map<String, Object> m : list) {
            System.out.println("m = " + m);
            String userId = (String) m.get("pb_userId");
            OrderVO orderVO = OrderVO.builder()
                    .title((String) m.get("title"))
                    .userId(userId)
                    .price((Integer) m.get("price"))
                    .product_no((Integer) m.get("no"))
                    .cnt((Integer) m.get("pb_cnt"))
                    .status(OrderEnum.READY.name())
                    .order_number(maxNum)
                    .tel1(orderParam.getTel1())
                    .tel2(orderParam.getTel2())
                    .tel3(orderParam.getTel3())
                    .address(orderParam.getAddress())
                    .receiver(orderParam.getReceiver())
                    .build();
            if (orderDAO.add(orderVO) < 0) throw new RuntimeException();
            BasketParam basketParam = BasketParam.builder()
                    .no(String.valueOf(m.get("pb_no")))
                    .userId(userId)
                    .build();
            basketDAO.del(basketParam);
            maxNum += 1;
        }

        return 0;
    }

    @Override
    public List<OrderVO> mylist(String userId) {
        return orderDAO.mylist(userId);
    }
}
