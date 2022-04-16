package com.summer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.summer.entity.Comment;
import com.summer.utils.R;


/**
 * 评论表(Comment)表服务接口
 *
 * @author summer
 * @since 2022-04-16 16:42:58
 */
public interface CommentService extends IService<Comment> {

    R getList(String type, Long articleId, Integer pageNum, Integer pageSize);

    R addComment(Comment comment);
}

