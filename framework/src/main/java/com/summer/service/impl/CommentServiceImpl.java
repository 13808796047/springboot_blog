package com.summer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summer.constants.CommentConstant;
import com.summer.entity.Comment;
import com.summer.entity.User;
import com.summer.mapper.CommentMapper;
import com.summer.service.CommentService;
import com.summer.service.UserService;
import com.summer.utils.BeanCopyUtils;
import com.summer.utils.R;
import com.summer.vo.CommentsVo;
import com.summer.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author summer
 * @since 2022-04-16 16:42:58
 */
@Slf4j
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public R getList(String type, Long articleId, Integer pageNum, Integer pageSize) {
        // 查询对应文章的根评论
        LambdaQueryWrapper<Comment> query = new LambdaQueryWrapper<>();
        query.eq(CommentConstant.ARTICLE_COMMENT.equals(type), Comment::getArticleId, articleId);
        query.eq(Comment::getRootId, -1);
        // 评论类型
        query.eq(Comment::getType, type);
        // 分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, query);
        List<CommentsVo> commentsVos = toCommentVoList(page.getRecords());

        // 查询所有根评论中的子评论
        List<CommentsVo> collect = commentsVos.stream().map(vo -> {
            // 查询对应的子评论
            List<CommentsVo> children = getChildren(vo.getId());
            vo.setChildren(children);
            return vo;
        }).collect(Collectors.toList());

        return R.success(new PageVo(commentsVos, page.getTotal()));
    }

    @Override
    public R addComment(Comment comment) {
        save(comment);
        return R.success();
    }

    private List<CommentsVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);

        List<CommentsVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }

    private List<CommentsVo> toCommentVoList(List<Comment> comments) {
        List<CommentsVo> commentsVos = BeanCopyUtils.copyBeanList(comments, CommentsVo.class);
        // 遍历vo集合查询用户昵称
        List<CommentsVo> collect = commentsVos.stream().map(vo -> {
            vo.setUsername(userService.getById(vo.getCreateBy()).getNickName());
            // 如果toCommentUserId不为-1才查询
            if (vo.getToCommentId() != -1) {
                User user = userService.getById(vo.getToCommentId());
                if (Objects.nonNull(user)) {
                    vo.setToCommentUserName(user.getNickName());
                }
            }
            return vo;
        }).collect(Collectors.toList());
        return collect;

    }
}
