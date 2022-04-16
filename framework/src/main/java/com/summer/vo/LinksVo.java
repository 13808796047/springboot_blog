package com.summer.vo;

import lombok.Data;

/**
 * @author Summer
 * @since 2022/4/16 11:40
 */
@Data
public class LinksVo {
    private Long id;

    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;
}
