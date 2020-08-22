package com.wzb.member.filter;

import com.wzb.member.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zebin Wang
 * @Title:
 * @Package
 * @Description: 拦截Controller请求，判断请求头是否携带token
 */
@Component
public class AuthenticationFilter extends HandlerInterceptorAdapter {

    @Autowired
    JwtUtil jwtUtil;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 是否登录
        Boolean isLogin = false;

        logger.info("拦截到请求");

        // 获取请求头 Authorization: Bearer jwtToken
        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 截取bearer 后的token
            final String token = authHeader.substring(7);

            Claims claims = null;
            try {
                claims = jwtUtil.parseJWT(token);
            } catch (ExpiredJwtException e) {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(401);
                response.getWriter().write("身份认证已过期");
                return false;
            }
            if (claims != null) isLogin = (Boolean) claims.get("isLogin");
        }

        if (!isLogin) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            response.getWriter().write("未通过身份认证");
        }

        return isLogin;
    }
}
