package com.dz.shop.Dao;

import com.dz.shop.entity.BasketParam;
import com.dz.shop.entity.BasketVO;

import java.util.HashMap;
import java.util.List;

public interface BasketDAO {
    long add(BasketParam basketParam);
    List<HashMap<String, Object>> list(String userId);

    long del(BasketParam basketParam);
}
