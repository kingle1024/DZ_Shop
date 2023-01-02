package com.dz.shop.member;

import com.dz.shop.entity.MemberVO;
import com.dz.shop.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
            @RequestBody MemberVO memberVO, HttpSession session,
            HttpServletRequest request
            ){
        System.out.println("MemberAPI.userStatus");

        MemberVO member = memberService.login(memberVO);
        Map<String, Object> map = new HashMap<>();

        if(member == null){
            map.put("status", false);
            return map;
        }

        session.setAttribute("isLogin", true);
        session.setAttribute("sessionUserId", member.getUserId());
        session.setAttribute("sessionName", member.getName());
        System.out.println("member = " + member.getName());
        map.put("status", true);
        map.put("href", request.getContextPath());

        return map;
    }

}
