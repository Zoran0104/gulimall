package com.zoran.gulimallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoran.common.utils.PageUtils;
import com.zoran.common.utils.Query;
import com.zoran.gulimallproduct.dao.AttrGroupDao;
import com.zoran.gulimallproduct.entity.AttrAttrgroupRelationEntity;
import com.zoran.gulimallproduct.entity.AttrGroupEntity;
import com.zoran.gulimallproduct.service.AttrGroupService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


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
        String key = (String) params.get("key");
        LambdaQueryWrapper<AttrGroupEntity> catalogQuery = new LambdaQueryWrapper<AttrGroupEntity>();
        if (!StringUtils.isEmpty(key)) {
            catalogQuery.and(obj -> {
                obj.eq(AttrGroupEntity::getAttrGroupId, key)
                        .or()
                        .like(AttrGroupEntity::getAttrGroupName, key);
            });
        }
        if (catalogId != 0) {
            catalogQuery.eq(AttrGroupEntity::getCatelogId, catalogId);
        }
        page = this.page(new Query<AttrGroupEntity>().getPage(params),catalogQuery);
        return new PageUtils(page);
    }


    @Override
    public void deleteRelation(AttrAttrgroupRelationEntity[] attrAttrgroupRelationEntities) {
        this.baseMapper.batchDeleteRelations(attrAttrgroupRelationEntities);
    }
}
