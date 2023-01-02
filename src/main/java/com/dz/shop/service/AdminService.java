package com.dz.shop.service;

import com.dz.shop.Page.PageUtil;

public interface AdminService {
    PageUtil pageUtil(String search, String pageIndex, String type);

    String userStatus(String userId, String userStatus);
}
