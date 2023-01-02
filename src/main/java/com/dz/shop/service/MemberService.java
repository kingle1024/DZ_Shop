package com.dz.shop.service;

import java.util.List;

import com.dz.shop.Page.BoardParam;
import com.dz.shop.Page.PageUtil;
import com.dz.shop.entity.MemberVO;

public interface MemberService {
	PageUtil pageUtil(String search, String pageIndex, String type);
}