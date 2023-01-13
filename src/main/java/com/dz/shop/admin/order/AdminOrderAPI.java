package com.dz.shop.admin.order;

import com.dz.shop.entity.AdminOrderParam;
import com.dz.shop.service.AdminOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/order/*")
public class AdminOrderAPI {
    private static final Logger logger = LoggerFactory.getLogger(AdminOrderAPI.class);

    @Autowired
    AdminOrderService adminOrderService;

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public Map<String, Object> status(
            @RequestBody Map<String, Object> map
    ){
        logger.info("AdminOrderAPI.status");
        System.out.println("map = " + map);
        AdminOrderParam adminOrderParam = AdminOrderParam.builder()
                .no((String) map.get("no"))
                .status((String) map.get("status"))
                .build();
        adminOrderService.updateDeliveryStatus(adminOrderParam);

        return map;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Map<String, Object> detail(
            @RequestBody Map<String, Object> map
    ){
        System.out.println("map = " + map);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("detail", adminOrderService.detail((String)map.get("no")));


        return resultMap;
    }
}
