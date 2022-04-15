package com.summer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summer.constants.ArticleConstant;
import com.summer.entity.Article;
import com.summer.mapper.ArticleMapper;
import com.summer.service.ArticleService;
import com.summer.service.CategoryService;
import com.summer.utils.BeanCopyUtils;
import com.summer.utils.R;
import com.summer.vo.ArticlesVo;
import com.summer.vo.HotArticleVo;
import com.summer.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author summer
 * @since 2022-04-15 23:10:52
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Lazy
    @Autowired
    private CategoryService categoryService;

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

    /**
     * 文章列表
     *
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @Override
    public R articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 查询条件
        LambdaQueryWrapper<Article> query = new LambdaQueryWrapper<>();
        // 如果有categoryId查询分类下的文章
        query.eq(Objects.nonNull(categoryId), Article::getCategoryId, categoryId);
        // 正式发布的文章
        query.eq(Article::getStatus, ArticleConstant.ARTICLE_STATUS_NORMAL);
        // isTop进行排序
        query.orderByDesc(Article::getIsTop);
        // 分页
        Page<Article> page = new Page<>(pageNum, pageSize);

        page(page, query);
        // 查询categoryName
        List<Article> records = page.getRecords();
        // 通过categoryId查询categoryName
        records = records.stream()
                .map(record -> record.setCategoryName(categoryService.getById(record.getCategoryId()).getName()))
                .collect(Collectors.toList());
        // 封装查询结果
        List<ArticlesVo> articlesVos = BeanCopyUtils.copyBeanList(records, ArticlesVo.class);
        PageVo pageVo = new PageVo(articlesVos, page.getTotal());
        return R.success(pageVo);
    }
}
