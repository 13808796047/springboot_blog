package com.summer.controller;

import com.summer.service.ArticleService;
import com.summer.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Summer
 * @since 2022/4/15 23:18
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;


    @GetMapping
    public R index(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);

    }

    @GetMapping("/hot")
    public R hotArticleList() {
        return articleService.getHotArticleList();
    }
}
