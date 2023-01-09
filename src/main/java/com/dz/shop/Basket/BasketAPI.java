package com.dz.shop.Basket;

import com.dz.shop.Page.PageUtil;
import com.dz.shop.entity.BasketParam;
import com.dz.shop.service.BasketService;
import com.dz.shop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/basket/*")
public class BasketAPI {
    private static final Logger logger = LoggerFactory.getLogger(BasketAPI.class);
    @Autowired
    BasketService basketService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Map<String, Object> basket(@RequestParam("no") String no, HttpServletResponse response, HttpSession session) {
        System.out.println("no = " + no);

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
    public String del(@RequestParam("no") String no, HttpSession session, Model model){
        String userId = (String) session.getAttribute("sessionUserId");
        BasketParam basketParam = BasketParam.builder()
                .userId(userId)
                .product_no(no)
                .build();

        basketService.del(basketParam);

        // 제품이름, 제품가격, basektvo 갯수

        model.addAttribute("list", list);

        return "basket/list";
    }
}
