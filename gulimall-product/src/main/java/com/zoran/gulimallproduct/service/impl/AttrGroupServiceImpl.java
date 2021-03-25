package com.zoran.gulimallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoran.common.utils.PageUtils;
import com.zoran.common.utils.Query;

import com.zoran.gulimallproduct.dao.AttrGroupDao;
import com.zoran.gulimallproduct.entity.AttrGroupEntity;
import com.zoran.gulimallproduct.service.AttrGroupService;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catalogId) {
        IPage<AttrGroupEntity> page;
        if (catalogId == 0) {
             page = this.page(new Query<AttrGroupEntity>().getPage(params),
                    new QueryWrapper<>());
        } else {
            String key = (String) params.get("key");
            QueryWrapper<AttrGroupEntity> catalogQuery = new QueryWrapper<AttrGroupEntity>().eq("catalog_id", catalogId);
            if (!StringUtils.isEmpty(key)) {
                catalogQuery.and(obj -> {
                    obj.eq("attr_group_id", key).or().like("attr_group_name", key);
                });
            }
            page = this.page(new Query<AttrGroupEntity>().getPage(params), catalogQuery);
        }
        return new PageUtils(page);
    }

}
