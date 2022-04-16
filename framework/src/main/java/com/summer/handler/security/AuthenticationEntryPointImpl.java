package com.summer.handler.security;

import com.alibaba.fastjson.JSON;
import com.summer.enums.ResponseStatus;
import com.summer.utils.R;
import com.summer.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author Summer
 * @since 2022/4/16 15:05
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        R<Serializable> fail = null;
        if (authException instanceof BadCredentialsException) {

            fail = R.fail(ResponseStatus.HTTP_STATUS_401.getResponseCode(), authException.getMessage());
        } else if (authException instanceof InsufficientAuthenticationException) {
            fail = R.fail(ResponseStatus.HTTP_STATUS_401.getResponseCode(), authException.getMessage());
        } else {
            fail = R.fail(ResponseStatus.HTTP_STATUS_401.getResponseCode(), "认证或授权失败");
        }
        // 响应

        WebUtils.renderString(response, JSON.toJSONString(fail));
    }
}
