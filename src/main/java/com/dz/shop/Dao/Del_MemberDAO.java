package com.dz.shop.Dao;

import com.dz.shop.entity.DelMember;

import java.util.Map;

public interface Del_MemberDAO {
    long add(Map<String, Object> map);
    DelMember findByUserId(String userId);

    long deleteByUserId(String userId);
}
