package com.dz.shop.service;

import com.dz.shop.entity.BasketParam;

import java.util.HashMap;
import java.util.List;

public interface BasketService {
    long add(BasketParam basketParam);

    List<HashMap<String, Object>> list(String userId);

    long del(BasketParam basketParam);
}
