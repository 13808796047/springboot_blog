package com.summer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summer.entity.Article;
import com.summer.mapper.ArticleMapper;
import com.summer.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * 文章表(Article)表服务实现类
 *
 * @author summer
 * @since 2022-04-15 23:10:52
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
