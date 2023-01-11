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
        System.out.println("map.get(\"checkList\") = " + map.get("checkList"));
        List<String> checkList = (List<String>) map.get("checkList");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < checkList.size(); i++) {
            sb.append(checkList.get(i));
            if (i != checkList.size() - 1) sb.append(",");
        }

        OrderParam orderParam = OrderParam.builder()
                .checkList(sb.toString())
                .userId(userId)
                .build();

        List<Map<String, Object>> prepareOrderList = orderService.list(orderParam);
        System.out.println("prepareOrderList = " + prepareOrderList);
        // 결제 대상 목록 추가

        // 읽어오기 /api/order/add

        // 추가하기
        // 목록 제거
        orderService.add(prepareOrderList);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", true);
        resultMap.put("url", request.getContextPath() + "/order/success");

        return resultMap;
    }


}
