package com.dz.shop.Dao;

import com.dz.shop.entity.MailVO;

import java.util.Map;

public interface MailDAO {
    long insert(MailVO mailVO);
    MailVO findByValueAndEmail(Map<String, Object> map);
    MailVO findByEmail(String email);
    long edit(Map<String, Object> map);
}
