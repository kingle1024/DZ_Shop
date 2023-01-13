package com.dz.shop.Utility;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class CustomUtil {
    public static Map<String, Object> getStringObjectMap(MultipartHttpServletRequest multipartRequest) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();
        Enumeration<?> e = multipartRequest.getParameterNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            map.put(key, new String(multipartRequest.getParameter(key).getBytes("ISO8859-1"), StandardCharsets.UTF_8));
        }
        return map;
    }
}
