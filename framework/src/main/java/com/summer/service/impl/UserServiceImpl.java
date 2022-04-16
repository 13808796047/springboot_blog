package com.summer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summer.entity.LoginUser;
import com.summer.entity.User;
import com.summer.mapper.UserMapper;
import com.summer.service.UserService;
import com.summer.utils.BeanCopyUtils;
import com.summer.utils.JwtUtil;
import com.summer.utils.R;
import com.summer.utils.RedisCache;
import com.summer.vo.LoginVo;
import com.summer.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 用户表(User)表服务实现类
 *
 * @author summer
 * @since 2022-04-16 11:59:19
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private AuthenticationManager authenticationManager;
    // redis操作类
    @Autowired
    private RedisCache redisCache;

    @Override
    public R login(User user) {
        // 验证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        // 返回Authenticate 对象
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        log.info("authenticate生成{}", authenticate);
        // 判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 获取UserId生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        // 生成token
        String token = JwtUtil.createJWT(userId);
        // 把用户信息存入redis
        redisCache.setCacheObject("login:" + userId, loginUser);
        // 把token和userinfo封装返回
        // 把User转换成UserVo
        UserVo userVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserVo.class);
        LoginVo loginVo = new LoginVo(token, userVo);

        return R.success(loginVo);
    }

    @Override
    public R logout() {
        // 获取token解析userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 获取UserId
        Long userId = loginUser.getUser().getId();
        // 删除redis中的用户信息
        redisCache.deleteObject("login:" + userId);

        return R.success();
    }
}
