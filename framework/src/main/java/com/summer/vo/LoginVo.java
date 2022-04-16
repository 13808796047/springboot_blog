package com.summer.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Summer
 * @since 2022/4/16 12:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {
    private String token;
    private UserVo user;
}
