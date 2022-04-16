package com.summer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summer.constants.LinkConstant;
import com.summer.entity.Link;
import com.summer.mapper.LinkMapper;
import com.summer.service.LinkService;
import com.summer.utils.BeanCopyUtils;
import com.summer.utils.R;
import com.summer.vo.LinksVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author summer
 * @since 2022-04-16 11:28:45
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public R getLinkList() {

        // 查询所有审核通过的Link
        LambdaQueryWrapper<Link> query = new LambdaQueryWrapper<>();

        query.eq(Link::getStatus, LinkConstant.LINK_STATUS_NORMAL);
        List<Link> links = list(query);
        // 转换VO
        List<LinksVo> linksVos = BeanCopyUtils.copyBeanList(links, LinksVo.class);
        return R.success(linksVos);
    }
}
