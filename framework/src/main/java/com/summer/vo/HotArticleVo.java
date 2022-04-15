package com.summer.vo;

import lombok.Data;

/**
 * @author Summer
 * @since 2022/4/16 0:21
 */
@Data
public class HotArticleVo {
    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
}
