package com.summer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.summer.entity.Category;
import com.summer.utils.R;


/**
 * 分类表(Category)表服务接口
 *
 * @author summer
 * @since 2022-04-16 01:06:02
 */
public interface CategoryService extends IService<Category> {

    R getCategoryList();
}

