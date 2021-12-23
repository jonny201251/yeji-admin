package com.haiying.yeji.common.config;
//https://blog.csdn.net/leeta521/article/details/119532691

import com.haiying.yeji.common.exception.PageTipException;
import com.haiying.yeji.model.entity.CheckUser;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        CheckUser user = (CheckUser) request.getSession().getAttribute("user");
        if (user == null) {
            throw new PageTipException("用户未登录");
        }
        return true;
    }
}
