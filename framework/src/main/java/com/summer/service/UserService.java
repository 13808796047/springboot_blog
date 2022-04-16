package com.summer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.summer.entity.User;
import com.summer.utils.R;


/**
 * 用户表(User)表服务接口
 *
 * @author summer
 * @since 2022-04-16 11:59:19
 */
public interface UserService extends IService<User> {

    R login(User user);

    R logout();

    R getUserInfo();
}

