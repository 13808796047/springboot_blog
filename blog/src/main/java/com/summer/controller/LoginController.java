package com.summer.controller;

import com.mysql.cj.util.StringUtils;
import com.summer.entity.User;
import com.summer.enums.ResponseStatus;
import com.summer.exception.SystemException;
import com.summer.service.UserService;
import com.summer.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Summer
 * @since 2022/4/16 11:56
 */
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R login(@RequestBody User user) {
        if (StringUtils.isNullOrEmpty(user.getUserName())) {
            throw new SystemException(ResponseStatus.HTTP_STATUS_401);
        }
        return userService.login(user);
    }
}
