package com.summer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summer.entity.Category;
import com.summer.mapper.CategoryMapper;
import com.summer.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * 分类表(Category)表服务实现类
 *
 * @author summer
 * @since 2022-04-16 01:06:02
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
