package com.dz.shop.member;

import com.dz.shop.Page.PageUtil;
import com.dz.shop.entity.MemberVO;
import com.dz.shop.service.AdminService;
import com.dz.shop.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/member/*")
public class MemberAPI {
    private static final Logger logger = LoggerFactory.getLogger(MemberAPI.class);
    @Autowired
    MemberService memberService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(
            @RequestBody MemberVO memberVO
            ){
        System.out.println("MemberAPI.userStatus");
        System.out.println("memberVO = " + memberVO);

        Map<String, Object> map = new HashMap<>();
        map.put("status", "true");

        return map;
    }

}
