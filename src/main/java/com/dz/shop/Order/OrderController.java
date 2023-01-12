package com.dz.shop.Order;

import com.dz.shop.entity.MemberVO;
import com.dz.shop.entity.OrderParam;
import com.dz.shop.entity.OrderVO;
import com.dz.shop.service.MemberService;
import com.dz.shop.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    OrderService orderService;
    @Autowired
    MemberService memberService;

    @RequestMapping(value = "/prepareOrder", method = RequestMethod.POST)
    public String list(HttpSession session, Model model, @RequestParam Map<String, Object> map){
        logger.info("OrderController.list");
        System.out.println("orderParam = " + map);
        String userId = (String) session.getAttribute("sessionUserId");
        String order_sheet = UUID.randomUUID().toString().substring(0, 10);

        OrderParam orderParam = OrderParam.builder()
                .checkNoList((String) map.get("checkNoList"))
                .userId(userId)
                .build();

        // 체크한 목록을 가져옴
        List<Map<String, Object>> list = orderService.list(orderParam);
        System.out.println("list = " + list);

        MemberVO memberVO = memberService.findByUserId(userId);
        //front에서 변조 방지

        //페이지를 벗어나는 경우에는 쓰레기 값으로 남음
        //다음 주문 페이지 이동 시에는 해당 uuid를 가져와서 select 진행

        model.addAttribute("list", list);
        model.addAttribute("member", memberVO);
        model.addAttribute("order_sheet", order_sheet);

        return "order/list";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String list(){
        return "order/success";
    }

    @RequestMapping(value = "/mylist", method = RequestMethod.GET)
    public String mylist(Model model, HttpSession session){
        String userId = (String) session.getAttribute("sessionUserId");
        List<OrderVO> mylist = orderService.mylist(userId);
        System.out.println("mylist = " + mylist);
        model.addAttribute("list", mylist);

        return "order/mylist";
    }
}
