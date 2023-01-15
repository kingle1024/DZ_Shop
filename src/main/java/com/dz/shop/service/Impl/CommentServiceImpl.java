package com.dz.shop.service.Impl;

import com.dz.shop.Dao.CommentDAO;
import com.dz.shop.Page.BoardParam;
import com.dz.shop.Page.PageUtil;
import com.dz.shop.comment.CommentService;
import com.dz.shop.entity.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentDAO commentDAO;

    @Autowired
    public CommentServiceImpl(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    @Override
    public PageUtil pageUtil(String search, String pageIndex, String type) {
        BoardParam param = BoardParam.builder()
                .pageIndex(pageIndex)
                .search(search)
                .type(type)
                .build();
        param.init();

        long totalCount;
        List<?> list;
        list = commentDAO.list(param);
        totalCount = commentDAO.listSize(param.getSearch());

        return PageUtil.builder()
                .list(list)
                .totalCount(totalCount)
                .pageSize(param.getPageSize())
                .pageIndex(param.getL_pageIndex())
                .build();
    }

    @Transactional
    @Override
    public CommentVO add(CommentVO comment) {
        comment.setRegister_date(LocalDateTime.now());
        comment.setDelete_yn(false);
        comment.setWriter(comment.getWriter());
        comment.setWriter_id(comment.getWriter_id());

        long result = commentDAO.add(comment);
        System.out.println("comment.getParent_no() = " + comment.getParent_no());
        if(result > 0 && comment.getParent_no() == null) {
            long commentNo = commentDAO.maxNo();
            System.out.println("commentNo = " + commentNo);
            comment.setParent_no(String.valueOf(commentNo));
            comment.setNo(String.valueOf(commentNo));
            commentDAO.edit(comment);
            System.out.println("comment = " + comment);
        }

        return comment;
    }

    @Override
    public CommentVO getProduct(String no) {
        return commentDAO.findByNo(no);
    }

    @Override
    public List<CommentVO> commentList() {
        return commentDAO.commentList();
    }

    @Override
    public List<CommentVO> findByParentNo(String parentNo) {
        return commentDAO.findByParentNo(parentNo);
    }

    @Override
    @Transactional
    public long reply(CommentVO commentVO) {
        if(commentDAO.updateStatusByNo(commentVO.getParent_no()) < 0) throw new RuntimeException();
        if(commentDAO.add(commentVO) < 0) throw new RuntimeException();
        return 1;
    }
}
