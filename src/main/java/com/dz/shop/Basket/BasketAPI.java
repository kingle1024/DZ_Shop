package com.dz.shop.Basket;

import com.dz.shop.entity.BasketParam;
import com.dz.shop.service.BasketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/basket/*")
public class BasketAPI {
    private static final Logger logger = LoggerFactory.getLogger(BasketAPI.class);
    @Autowired
    BasketService basketService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Map<String, Object> basket(@RequestParam("no") String no, HttpSession session) {
        logger.info("BasketAPI.basket");

        BasketParam basketParam = BasketParam.builder()
                .product_no(no)
                .userId((String) session.getAttribute("sessionUserId"))
                .build();

        System.out.println("basketParam = " + basketParam);
        long result = basketService.add(basketParam);

        System.out.println("result = " + result);
        Map<String, Object> resultMap = new HashMap<>();
        if(result > 0) {
            resultMap.put("status", true);
        }else{
            resultMap.put("status", false);
        }

        return resultMap;
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public Map<String, Object> del(@RequestParam("no") String no, HttpSession session){
        String userId = (String) session.getAttribute("sessionUserId");
        BasketParam basketParam = BasketParam.builder()
                .userId(userId)
                .product_no(no)
                .build();

        long result = basketService.del(basketParam);

        Map<String, Object> resultMap = new HashMap<>();
        if(result > 0) {
            resultMap.put("status", true);
        }else{
            resultMap.put("status", false);
        }

        return resultMap;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public Map<String, Object> edit(
            @RequestBody BasketParam basketParam,
            @RequestParam("type") String type,
            HttpSession session){
        String userId = (String) session.getAttribute("sessionUserId");
        int cnt = basketParam.getCnt();
        if(type.equals("up")){
            cnt += 1;
        }else if(type.equals("down")){
            cnt -= 1;
        }
        basketParam.setCnt(cnt);
        basketParam.setUserId(userId);

        long result = basketService.edit(basketParam);

        Map<String, Object> resultMap = new HashMap<>();
        if(result > 0) {
            resultMap.put("status", true);
        }else{
            resultMap.put("status", false);
        }

        return resultMap;
    }

    @RequestMapping(value = "/prepareOrder", method = RequestMethod.POST)
    public Map<String, Object> prepareOrder(HttpServletRequest request
            ){

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("href", request.getContextPath()+"/order/list");

        return resultMap;
    }
}
