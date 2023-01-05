package com.dz.shop.Dao;

import com.dz.shop.entity.Popularity;

public interface PopularityDAO {
    Popularity findByBnoAndUserIdAndIsDelete(Popularity popularity);

    long insert(Popularity popularity);

    long update(Popularity popularity);
}
