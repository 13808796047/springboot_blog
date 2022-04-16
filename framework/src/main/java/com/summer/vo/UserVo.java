package com.summer.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Summer
 * @since 2022/4/16 12:35
 */
@Data
@Accessors(chain = true)
public class UserVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;
}
