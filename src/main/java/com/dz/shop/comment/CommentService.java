package com.dz.shop.comment;

import com.dz.shop.Page.PageUtil;
import com.dz.shop.entity.CommentVO;

import java.util.List;
import java.util.Map;

public interface CommentService {
    PageUtil pageUtil(String parent_no, String pageIndex, String type);
    CommentVO add(CommentVO comment);
    CommentVO getProduct(String no);
    List<CommentVO> commentList();
    List<CommentVO> findByParentNo(String parentNo);

    long reply(CommentVO commentVO);

    long del(Map<String, Object> map);
}
