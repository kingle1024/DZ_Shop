package com.dz.shop.service.Impl;

import com.dz.shop.Dao.Del_MemberDAO;
import com.dz.shop.Page.BoardParam;
import com.dz.shop.Page.PageUtil;
import com.dz.shop.entity.MemberEnum;
import com.dz.shop.Dao.AdminDAO;
import com.dz.shop.entity.MemberVO;
import com.dz.shop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminDAO adminDAO;
    private final Del_MemberDAO del_memberDAO;

    @Autowired
    public AdminServiceImpl(AdminDAO adminDAO, Del_MemberDAO del_memberDAO) {
        this.adminDAO = adminDAO;
        this.del_memberDAO = del_memberDAO;
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

    @Override
    @Transactional
    public long del(String userId) {
        Map<String, Object> map = new HashMap<>();

        String userStatus = changeUserStatus(MemberEnum.DELETE.name().toUpperCase());
        map.put("userId", userId);
        map.put("userStatus", userStatus);
        MemberVO byUserId = adminDAO.findByUserId(userId);

        if(byUserId == null) return -1;
        if(adminDAO.userStatus(map) < 0) return -1;

        return del_memberDAO.add(map);
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
