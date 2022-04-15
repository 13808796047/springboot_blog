package com.summer.controller;

import com.summer.entity.Article;
import com.summer.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Summer
 * @since 2022/4/15 23:18
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping()
    public List<Article> test() {
        return articleService.list();
    }
}
