package com.dz.shop.service.Impl;

import com.dz.shop.Page.BoardParam;
import com.dz.shop.Page.PageUtil;
import com.dz.shop.admin.MemberEnum;
import com.dz.shop.Dao.AdminDAO;
import com.dz.shop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminDAO adminDAO;

    @Autowired
    public AdminServiceImpl(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    public PageUtil pageUtil(String search, String pageIndex, String type){
        BoardParam param = BoardParam.builder()
                .pageIndex(pageIndex)
                .search(search)
                .type(type)
                .build();
        param.init();

        long totalCount;
        List<?> list;
        list = adminDAO.memberList(param);
        totalCount = adminDAO.listSize(param.getSearch());

        return PageUtil.builder()
                .list(list)
                .totalCount(totalCount)
                .pageSize(param.getPageSize())
                .pageIndex(param.getL_pageIndex())
                .build();
    }

    @Override
    public String userStatus(String userIdParam, String userStatusParam) {
        System.out.println("AdminServiceImpl.userStatus");
        Map<String, Object> map = new HashMap<>();

        String userStatus = changeUserStatus(userStatusParam.toUpperCase());

        map.put("userId", userIdParam);
        map.put("userStatus", userStatus);
        System.out.println(map);
        int reuslt = adminDAO.userStatus(map);
        if(reuslt < 0){
            return null;
        }

        return userStatus;
    }

    private static String changeUserStatus(String userStatus) {

        switch (userStatus){
            case "STOP":{
                userStatus = MemberEnum.USE.name();
                break;
            }
            case "USE":{
                userStatus = MemberEnum.STOP.name();
                break;
            }
        }
        return userStatus;
    }
}
