package com.summer.controller;

import com.summer.constants.CommentConstant;
import com.summer.entity.Comment;
import com.summer.service.CommentService;
import com.summer.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Summer
 * @since 2022/4/16 17:16
 */
@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public R index(Long articleId, Integer pageNum, Integer pageSize) {

        return commentService.getList(CommentConstant.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    @PostMapping
    public R create(@RequestBody Comment comment) {

        return commentService.addComment(comment);
    }

    @GetMapping("/link")
    public R linkComment(Integer pageNum, Integer pageSize) {

        return commentService.getList(CommentConstant.LINK_COMMENT, null, pageNum, pageSize);
    }
}
