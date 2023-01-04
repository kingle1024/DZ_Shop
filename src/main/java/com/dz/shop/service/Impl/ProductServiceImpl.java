package com.dz.shop.service.Impl;

import com.dz.shop.Dao.AdminProductDAO;
import com.dz.shop.Dao.BoardFileDAO;
import com.dz.shop.Page.BoardParam;
import com.dz.shop.Page.PageUtil;
import com.dz.shop.entity.BoardFile;
import com.dz.shop.entity.ProductVO;
import com.dz.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final AdminProductDAO adminProductDAO;
    private final BoardFileDAO boardFileDAO;

    @Autowired
    public ProductServiceImpl(AdminProductDAO adminProductDAO, BoardFileDAO boardFileDAO) {
        this.adminProductDAO = adminProductDAO;
        this.boardFileDAO = boardFileDAO;
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
        list = adminProductDAO.list(param);
        totalCount = adminProductDAO.listSize(param.getSearch());

        return PageUtil.builder()
                .list(list)
                .totalCount(totalCount)
                .pageSize(param.getPageSize())
                .pageIndex(param.getL_pageIndex())
                .build();
    }

    @Transactional
    @Override
    public long add(ProductVO product, List<BoardFile> boardFiles) {

        long result = adminProductDAO.add(product);
        long productNo = adminProductDAO.maxNo();

        for(BoardFile bf : boardFiles){
            bf.setNumber((int) productNo);
            boardFileDAO.add(bf);
        }

        return result;
    }

    @Override
    public ProductVO getProduct(String no) {
        return adminProductDAO.findByNo(no);
    }
}
