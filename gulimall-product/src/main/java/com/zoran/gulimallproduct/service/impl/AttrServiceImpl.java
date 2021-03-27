package com.zoran.gulimallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoran.common.constant.ProductConstant;
import com.zoran.common.utils.PageUtils;
import com.zoran.common.utils.Query;
import com.zoran.gulimallproduct.dao.AttrDao;
import com.zoran.gulimallproduct.dao.AttrGroupDao;
import com.zoran.gulimallproduct.dao.CategoryDao;
import com.zoran.gulimallproduct.entity.AttrAttrgroupRelationEntity;
import com.zoran.gulimallproduct.entity.AttrEntity;
import com.zoran.gulimallproduct.entity.CategoryEntity;
import com.zoran.gulimallproduct.service.AttrAttrgroupRelationService;
import com.zoran.gulimallproduct.service.AttrService;
import com.zoran.gulimallproduct.service.CategoryService;
import com.zoran.gulimallproduct.vo.AttrRespVo;
import com.zoran.gulimallproduct.vo.AttrVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service("attrService")
@AllArgsConstructor
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    private final AttrAttrgroupRelationService attrAttrgroupRelationService;

    private final AttrGroupDao attrGroupDao;

    private final CategoryDao categoryDao;

    private final CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.save(attrEntity);
        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrId(attr.getAttrId());
            attrAttrgroupRelationService.save(attrAttrgroupRelationEntity);
        }

    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type) {
        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<AttrEntity>()
                .eq(AttrEntity::getAttrType,
                        "base".equalsIgnoreCase(type) ?
                                ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()
                                : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        if (!catelogId.equals(0L)) {
            wrapper.eq(AttrEntity::getCatelogId, catelogId);
        }
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(wrapper1 -> {
                wrapper1.eq(AttrEntity::getAttrId, key).or().like(AttrEntity::getAttrName, key);
            });
        }
        IPage<AttrEntity> attrEntityIPage = this.baseMapper.selectPage(new Query<AttrEntity>().getPage(params), wrapper);
        List<AttrEntity> records = attrEntityIPage.getRecords();
        List<AttrRespVo> respVos = records.stream().map(record -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(record, attrRespVo);
            if ("base".equals(type)) {
                AttrAttrgroupRelationEntity attrId = attrAttrgroupRelationService.getOne(new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                        .eq(AttrAttrgroupRelationEntity::getAttrId, record.getAttrId()));
                Optional.ofNullable(attrId)
                        .ifPresent(entity -> attrRespVo.setGroupName(attrGroupDao.selectById(attrId.getAttrGroupId()).getAttrGroupName()));
            }
            CategoryEntity categoryEntity = categoryDao.selectById(record.getCatelogId());
            Optional.ofNullable(categoryEntity).ifPresent(entity -> attrRespVo.setCatelogName(entity.getName()));
            return attrRespVo;

        }).collect(Collectors.toList());
        PageUtils pageUtils = new PageUtils(attrEntityIPage);
        pageUtils.setList(respVos);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo attrRespVo = new AttrRespVo();
        AttrEntity attrEntity = this.baseMapper.selectById(attrId);
        BeanUtils.copyProperties(attrEntity, attrRespVo);
        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            AttrAttrgroupRelationEntity attrRelationId = attrAttrgroupRelationService.getOne(new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                    .eq(AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId()));
            Optional.ofNullable(attrRelationId)
                    .ifPresent(entity -> {
                        attrRespVo.setGroupName(attrGroupDao.selectById(attrRelationId.getAttrGroupId()).getAttrGroupName());
                        attrRespVo.setAttrGroupId(entity.getAttrGroupId());
                    });
        }
        Long[] catelogPath = categoryService.findCatelogPath(attrEntity.getCatelogId());
        attrRespVo.setCatelogPath(catelogPath);
        CategoryEntity categoryEntity = categoryService.getById(attrEntity.getCatelogId());
        Optional.ofNullable(categoryEntity).ifPresent(entity -> attrRespVo.setCatelogName(entity.getName()));

        return attrRespVo;
    }

    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);
        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrId(attr.getAttrId());
            // 对应的attr可能在表中不存在 此时应执行insert操作
            attrAttrgroupRelationService.saveOrUpdate(attrAttrgroupRelationEntity,
                    new LambdaUpdateWrapper<AttrAttrgroupRelationEntity>()
                            .eq(AttrAttrgroupRelationEntity::getAttrId, attr.getAttrId()));
        }
    }
}
