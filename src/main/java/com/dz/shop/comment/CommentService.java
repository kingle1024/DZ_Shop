package com.dz.shop.comment;

import com.dz.shop.Page.PageUtil;
import com.dz.shop.entity.CommentVO;

public interface CommentService {
    PageUtil pageUtil(String parent_no, String pageIndex, String type);
    CommentVO add(CommentVO comment);
    CommentVO getProduct(String no);
}
