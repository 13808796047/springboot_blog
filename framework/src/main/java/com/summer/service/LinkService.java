package com.summer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.summer.entity.Link;
import com.summer.utils.R;


/**
 * 友链(Link)表服务接口
 *
 * @author summer
 * @since 2022-04-16 11:28:45
 */
public interface LinkService extends IService<Link> {

    R getLinkList();
}

