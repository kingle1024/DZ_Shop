package com.dz.shop.Interceptor;

import com.dz.shop.Utility.SessionAttribute;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Object userId = session.getAttribute(SessionAttribute.userid.toString());
        Object isAdmin = session.getAttribute(SessionAttribute.admin.toString());

        if(userId == null){
            response.sendRedirect(request.getContextPath()+"/member/loginForm.do");
            return false;
        }else if(isAdmin == null){
            response.sendRedirect(request.getContextPath()+"/");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("LoginInterceptor.postHandle");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("LoginInterceptor.afterCompletion");
    }
}
