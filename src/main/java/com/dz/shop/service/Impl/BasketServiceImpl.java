package com.dz.shop.service.Impl;

import com.dz.shop.Dao.BasketDAO;
import com.dz.shop.entity.BasketParam;
import com.dz.shop.entity.BasketVO;
import com.dz.shop.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BasketServiceImpl implements BasketService {
    @Autowired
    BasketDAO basketDAO;

    @Override
    public long add(BasketParam basketParam) {
        return basketDAO.add(basketParam);
    }

    @Override
    public List<HashMap<String, Object>> list(String userId) {
        List<HashMap<String, Object>> list = basketDAO.list(userId);
        return list;
    }

    @Override
    public long edit(BasketParam basketParam) {
        System.out.println("BasketServiceImpl.edit");
        System.out.println("basketParam = " + basketParam);
        return basketDAO.edit(basketParam);
    }

    @Override
    public long del(BasketParam basketParam) {
        return basketDAO.del(basketParam);
    }

    @Override
    public BasketVO findByNo(String no) {
        return basketDAO.findByNo(no);
    }
}
