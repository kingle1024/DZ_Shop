package com.dz.shop.service;

import com.dz.shop.entity.Popularity;
import java.util.Map;

public interface PopularityService {
    Map<String, Object> add(Popularity popularity);

    String findByBnoAndUserIdAndIsDelete(String no, String userId);
}
