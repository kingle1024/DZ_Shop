package com.dz.shop.Dao;

import com.dz.shop.Page.BoardParam;
import com.dz.shop.entity.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CommentDAO {
    List<CommentVO> list(BoardParam boardParam);
    List<CommentVO> list();
    long listSize(String search);
    long add(CommentVO comment);
    long maxNo();
    CommentVO findByNo(String no);
    long edit(CommentVO comment);

    List<CommentVO> commentList();
    List<CommentVO> findByParentNo(String parentNo);

    long updateStatusByNo(String parent_no);
}
