package com.summer.vo;

import lombok.Data;

/**
 * @author Summer
 * @since 2022/4/16 11:13
 */
@Data
public class ArticleVo extends ArticlesVo {

    private Long categoryId;
    private String content;
}
