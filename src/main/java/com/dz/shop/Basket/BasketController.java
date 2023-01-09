package com.dz.shop.Basket;

import com.dz.shop.entity.BasketParam;
import com.dz.shop.service.BasketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/basket")
public class BasketController {
    private static final Logger logger = LoggerFactory.getLogger(BasketController.class);
    @Autowired
    BasketService basketService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpSession session, Model model){
        String userId = (String) session.getAttribute("sessionUserId");
        List<HashMap<String, Object>> list = basketService.list(userId);
        // 제품이름, 제품가격, basektvo 갯수

        model.addAttribute("list", list);

        return "basket/list";
    }

}
