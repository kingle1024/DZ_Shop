package com.dz.shop.service;

import com.dz.shop.entity.MailVO;
import com.dz.shop.entity.MemberVO;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface MailService {
    long welcomSendMail(MemberVO memberVO);
    long sendSearchIdMail(Map<String, Object> map) throws UnsupportedEncodingException;
    long sendSearchPwdMail(Map<String, Object> map) throws UnsupportedEncodingException;
    String readMailTemplate(String templatePath);
    MailVO checkValue(Map<String, Object> map);
    String createRetValue(Map<String, Object> map);
    long validRetValue(Map<String, Object> map);
}
