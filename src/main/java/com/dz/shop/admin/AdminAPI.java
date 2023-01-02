package com.dz.shop.admin;

import com.dz.shop.Page.PageUtil;
import com.dz.shop.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/*")
public class AdminAPI {
    private static final Logger logger = LoggerFactory.getLogger(AdminAPI.class);
    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/userStatus", method = RequestMethod.GET)
    public Map<String, Object> userStatus(
            @RequestParam String userId,
            @RequestParam String userStatusParam
    ){
        System.out.println("MemberAPI.userStatus");

        String userStatus = adminService.userStatus(userId, userStatusParam);

        Map<String, Object> map = new HashMap<>();
        if(userStatus == null) {
            map.put("status", "false");
        } else {
            map.put("status", "true");
        }

        map.put("userStatus", userStatus);

        return map;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Map<String, Object> search(
            @RequestParam String search,
            @RequestParam String pageIndex
    ){
        System.out.println("AdminAPI.search");
        Map<String, Object> map = new HashMap<>();
        System.out.println("search = " + search);
        System.out.println("pageIndex = " + pageIndex);
        PageUtil pageUtil = adminService.pageUtil(search, pageIndex, "");

        map.put("list", pageUtil.getList());
        map.put("pager", pageUtil.paper());

        return map;
    }
}
