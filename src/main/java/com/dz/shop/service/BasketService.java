package com.dz.shop.service;

import com.dz.shop.entity.BasketParam;
import com.dz.shop.entity.BasketVO;

import java.util.HashMap;
import java.util.List;

public interface BasketService {
    long add(BasketParam basketParam);

    List<HashMap<String, Object>> list(String userId);

    long edit(BasketParam basketParam);

    long del(BasketParam basketParam);

    BasketVO findByNo(String no);
}
