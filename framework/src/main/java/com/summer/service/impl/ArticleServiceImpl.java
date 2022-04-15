package com.summer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summer.constants.ArticleConstant;
import com.summer.entity.Article;
import com.summer.mapper.ArticleMapper;
import com.summer.service.ArticleService;
import com.summer.utils.BeanCopyUtils;
import com.summer.utils.R;
import com.summer.vo.HotArticleVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章表(Article)表服务实现类
 *
 * @author summer
 * @since 2022-04-15 23:10:52
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public R getHotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 查询正式文章
        queryWrapper.eq(Article::getStatus, ArticleConstant.ARTICLE_STATUS_NORMAL);
        // 按浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 分页
        Page<Article> page = new Page<>(1, 10);
        this.page(page, queryWrapper);
        List<Article> articles = page.getRecords();
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return R.success(hotArticleVos);
    }
}
