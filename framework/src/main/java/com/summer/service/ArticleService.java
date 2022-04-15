package com.summer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.summer.entity.Article;
import com.summer.utils.R;


/**
 * 文章表(Article)表服务接口
 *
 * @author summer
 * @since 2022-04-15 23:10:51
 */
public interface ArticleService extends IService<Article> {

    R getHotArticleList();

    R articleList(Integer pageNum, Integer pageSize, Long categoryId);
}

