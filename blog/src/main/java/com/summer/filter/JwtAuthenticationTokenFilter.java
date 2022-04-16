package com.summer.filter;

import com.alibaba.fastjson.JSON;
import com.mysql.cj.util.StringUtils;
import com.summer.entity.LoginUser;
import com.summer.enums.ResponseStatus;
import com.summer.utils.JwtUtil;
import com.summer.utils.R;
import com.summer.utils.RedisCache;
import com.summer.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Summer
 * @since 2022/4/16 14:17
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中的token
        String token = request.getHeader("token");
        if (StringUtils.isNullOrEmpty(token)) {
            // 说明该接口不需要登录，直接放行
            filterChain.doFilter(request, response);
            return;
        }
        // 解析获取userId
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // 超时
            // token非法
            // 响应重新登录
            R<Serializable> fail = R.fail(ResponseStatus.HTTP_STATUS_401.getResponseCode(), ResponseStatus.HTTP_STATUS_401.getDescription());
            WebUtils.renderString(response, JSON.toJSONString(fail));
            return;
        }
        String userId = claims.getSubject();
        // 从redis中获取用户信息

        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        // 如果获取不到
        if (Objects.isNull(loginUser)) {
            R<Serializable> fail = R.fail(ResponseStatus.HTTP_STATUS_401.getResponseCode(), ResponseStatus.HTTP_STATUS_401.getDescription());
            WebUtils.renderString(response, JSON.toJSONString(fail));
            return;
        }
        // 存入securityContextHolder

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request, response);
    }
}
