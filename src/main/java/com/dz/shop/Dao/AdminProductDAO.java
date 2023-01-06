package com.dz.shop.Dao;

import com.dz.shop.Page.BoardParam;
import com.dz.shop.entity.ProductVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminProductDAO {
    List<ProductVO> list(BoardParam boardParam);
    long listSize(String search);
    long add(ProductVO product);
    long maxNo();
    ProductVO findByNo(String no);
    long edit(ProductVO product);
    long del(Map<String, Object> map);
}
