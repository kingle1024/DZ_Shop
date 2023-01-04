package com.dz.shop.service;

import com.dz.shop.Page.PageUtil;
import com.dz.shop.entity.BoardFile;
import com.dz.shop.entity.ProductVO;

import java.util.List;

public interface ProductService {
    PageUtil pageUtil(String search, String pageIndex, String type);

    long add(ProductVO product, List<BoardFile> boardFiles);
    ProductVO getProduct(String id);
}
