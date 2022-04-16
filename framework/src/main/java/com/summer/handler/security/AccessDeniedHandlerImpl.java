package com.summer.handler.security;

import com.alibaba.fastjson.JSON;
import com.summer.enums.ResponseStatus;
import com.summer.utils.R;
import com.summer.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author Summer
 * @since 2022/4/16 15:11
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();

        R<Serializable> fail = R.fail(ResponseStatus.HTTP_STATUS_401.getResponseCode());
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(fail));
    }
}
