package com.dz.shop.Order;

import com.dz.shop.entity.OrderParam;
import com.dz.shop.service.BasketService;
import com.dz.shop.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order/*")
public class OrderAPI {
    private static final Logger logger = LoggerFactory.getLogger(OrderAPI.class);

    @Autowired
    OrderService orderService;

    @Autowired
    BasketService basketService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map<String, Object> add(
            @RequestBody Map<String, Object> map, HttpSession session,
            HttpServletRequest request
    ) {
        logger.info("OrderAPI.add");
        String userId = (String) session.getAttribute("sessionUserId");
        List<String> checkList = (List<String>) map.get("checkNoList");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < checkList.size(); i++) {
            sb.append(checkList.get(i));
            if (i != checkList.size() - 1) sb.append(",");
        }

        System.out.println("map = " + map);

        OrderParam orderParam = OrderParam.builder()
                .checkNoList(sb.toString())
                .userId(userId)
                .receiver((String) map.get("receiver"))
                .tel1((String) map.get("tel1"))
                .tel2((String) map.get("tel2"))
                .tel3((String) map.get("tel3"))
                .address((String) map.get("address"))
                .build();

        List<Map<String, Object>> prepareOrderList = orderService.list(orderParam);


        orderService.add(prepareOrderList, orderParam);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", true);
        resultMap.put("url", request.getContextPath() + "/order/success");

        return resultMap;
    }


}
