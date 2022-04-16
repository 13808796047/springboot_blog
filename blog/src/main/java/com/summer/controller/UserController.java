package com.summer.controller;

import com.summer.service.UserService;
import com.summer.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Summer
 * @since 2022/4/16 21:33
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public R show() {

        return userService.getUserInfo();

    }
}
