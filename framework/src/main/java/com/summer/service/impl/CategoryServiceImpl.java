package com.summer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summer.constants.ArticleConstant;
import com.summer.constants.CategoryConstant;
import com.summer.entity.Article;
import com.summer.entity.Category;
import com.summer.mapper.CategoryMapper;
import com.summer.service.ArticleService;
import com.summer.service.CategoryService;
import com.summer.utils.BeanCopyUtils;
import com.summer.utils.R;
import com.summer.vo.CategoriesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author summer
 * @since 2022-04-16 01:06:02
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public R getCategoryList() {
        // 查询文章表，状态为已经发布的文章

        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();

        articleWrapper.eq(Article::getStatus, ArticleConstant.ARTICLE_STATUS_NORMAL);
        List<Article> articles = articleService.list(articleWrapper);

        // 获取分类id并且去重
        Set<Long> categoryIds = articles.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        // 查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(category -> CategoryConstant.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        // 封装vo
        List<CategoriesVo> categoriesVos = BeanCopyUtils.copyBeanList(categories, CategoriesVo.class);
        return R.success(categoriesVos);
    }
}
