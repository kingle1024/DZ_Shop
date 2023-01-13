package com.dz.shop.Utility;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class ChatUtil {
    public static String getSessionIdFromCookie(
            HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        boolean hasChatCookie = false;
        String chatSessionId = null;
        String cookieName = "cookieChat";
        try {
            for (Cookie c : cookies) {
                if (c.getName().equals(cookieName)) {
                    hasChatCookie = true;
                    c.setMaxAge(60 * 60);
                    response.addCookie(c);
                    chatSessionId = c.getValue();
                }
            }
        }catch (Exception e){ }

        if(!hasChatCookie){
            String uuid = UUID.randomUUID().toString().replace("-", "");
            Cookie cookie = new Cookie(cookieName, uuid);
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);
            chatSessionId = uuid;
            System.out.println("uuid = " + uuid);
        }
        return chatSessionId;
    }
}
